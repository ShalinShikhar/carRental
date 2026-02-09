package reservation;

import product.VehicleInventoryManager;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationManager {

    private final VehicleInventoryManager vehicleInventoryManager;
    private final ReservationRepository reservationRepository;

    private final AtomicInteger reservationIdGenerator=new AtomicInteger(20000);

    public ReservationManager(VehicleInventoryManager inventory) {
        this.vehicleInventoryManager = inventory;
        this.reservationRepository = new ReservationRepository();
        this.vehicleInventoryManager.setReservationRepository(this.reservationRepository);

    }

    public Optional<Reservation> findByID(int reservationId)
    {
        return reservationRepository.findById(reservationId);
    }

    public Reservation createReservation(int vehicleId, User user, LocalDate from,LocalDate to,ReservationType type)
    {
        int reservationId=reservationIdGenerator.getAndIncrement();
        boolean reserved=vehicleInventoryManager.reserve(vehicleId,reservationId,from,to);
        if(!reserved)
        {
            throw new RuntimeException("Vehicle not available for selected dates");
        }
        Reservation reservation=new Reservation(reservationId,vehicleId,user.getUserId(),from,to,type);
        reservationRepository.save(reservation);
        return reservation;

    }

    public void cancelReservation(int reservationId)
    {
        Optional<Reservation> opt=reservationRepository.findById(reservationId);
        if(!opt.isPresent())
        {
            throw new RuntimeException("Reservation not found");
        }
        Reservation r=opt.get();
        r.setReservationStatus(ReservationStatus.CANCELLED);
        vehicleInventoryManager.release(r.getVehicleId(),r.getReservationId());
        reservationRepository.remove(reservationId);
    }

    public void startTrip(int reservationId)
    {
        Reservation r=reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("Reservation not found"));
        r.setReservationStatus(ReservationStatus.IN_USE);
    }

    public void submitVehicle(int reservationId)
    {
        Reservation r=reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("Reservation not found"));
        r.setReservationStatus(ReservationStatus.COMPLETED);
        vehicleInventoryManager.release(r.getVehicleId(), r.getReservationId());
    }

    public void remove(int reservationId) {
        reservationRepository.remove(reservationId);
    }


}
