package product;

import reservation.Reservation;
import reservation.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class VehicleInventoryManager {

    private final ConcurrentMap<Integer,Vehicle> vehicles=new ConcurrentHashMap<>();

    private final ConcurrentMap<Integer, List<Integer>> vehicleBookingIds=new ConcurrentHashMap<>();

    private final ConcurrentMap<Integer, ReentrantLock> vehicleLocks=new ConcurrentHashMap<>();

    private ReservationRepository reservationRepository;

    public void addVehicle(Vehicle vehicle)
    {
        vehicles.put(vehicle.getVehicleID(),vehicle);
    }
    public Optional<Vehicle> getVehicle(int vehicleID)
    {
        return Optional.ofNullable(vehicles.get(vehicleID));
    }
    public void setReservationRepository(ReservationRepository reservationRepository)
    {
        this.reservationRepository=reservationRepository;
    }
    public ReentrantLock lockForVehicle(int vehicleId)
    {
        vehicleLocks.putIfAbsent(vehicleId,new ReentrantLock());
        return vehicleLocks.get(vehicleId);
    }
    // ---------------- Availability Check ------------

    public boolean isAvailable(int vehicleID, LocalDate from ,LocalDate to)
    {
        Vehicle vehicle=vehicles.get(vehicleID);
        if(vehicle==null){
            return false;
        }
        if(vehicle.getVehicleStatus()==VehicleStatus.MAINTENANCE)
        {
            return false;
        }
        DateInterval requested=new DateInterval(from,to);
        List<Integer> reservationIds=vehicleBookingIds.get(vehicleID);
        if(reservationIds==null || reservationIds.isEmpty())
        {
            return true;
        }
        for(int reservationID : reservationIds)
        {
            Reservation reservation=reservationRepository.findById(reservationID);
            LocalDate bookedFrom=reservation.getDateBookedFrom();
            LocalDate bookedTill=reservation.getDateBookedTo();
            DateInterval bookInterval=new DateInterval(bookedFrom,bookedTill);
            if(bookInterval.overlaps(requested))
            {
                return false;
            }

        }
        return true;
    }

    // ---------------- Atomic Booking ----------------

    public boolean reserve(int vehicleId,int reservationID,LocalDate from ,LocalDate to)
    {
        ReentrantLock lock=vehicleLocks.get(vehicleId);
        lock.lock();
        try{
            if(!isAvailable(vehicleId,from,to))
            {
                return false;
            }
            vehicleBookingIds.putIfAbsent(vehicleId,new ArrayList<>());
            vehicleBookingIds.get(vehicleId).add(reservationID);
            vehicles.get(vehicleId).setVehicleStatus(VehicleStatus.BOOKED);
            return true;
        }finally{
            lock.unlock();
        }
    }
    // --------------- Atomic Release -----------------
    public void release(int vehicleId,int reservationId)
    {
        ReentrantLock lock=lockForVehicle(vehicleId);
        lock.lock();
        try{
            List<Integer> ids=vehicleBookingIds.get(vehicleId);
            if(ids!=null)
            {
                ids.remove(Integer.valueOf(reservationId));
            }
            List<Integer> stillBooked=vehicleBookingIds.get(vehicleId);
            if(stillBooked==null || stillBooked.isEmpty())
            {
                vehicles.get(vehicleId).setVehicleStatus(VehicleStatus.AVAILABLE);
            }
        }
        finally {
            lock.unlock();
        }
    }

    // --------------- Search --------------------------

    public List<Vehicle> getAvailableVehicles(VehicleType vehicleType,LocalDate from,LocalDate to)
    {
        return vehicles.values().stream().filter(v->v.getVehicleType()==vehicleType).filter(v->isAvailable(v.getVehicleID(),from,to)).collect(Collectors.toList());
    }

}
