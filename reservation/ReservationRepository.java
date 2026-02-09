package reservation;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationRepository {

    private final Map<Integer,Reservation> reservations;

    public ReservationRepository() {
        this.reservations = new ConcurrentHashMap<>();
    }

    public void save(Reservation reservation)
    {
        reservations.put(reservation.getReservationId(),reservation);
    }

    public Optional<Reservation> findById(int reservationId)
    {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    public void remove(int reservationId)
    {
        reservations.remove(reservationId);
    }
    public Map<Integer,Reservation> getAll(){
        return reservations;
    }
}
