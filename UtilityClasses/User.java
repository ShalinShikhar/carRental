package UtilityClasses;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String  name;
    private String email;
    private List<Reservation> reservations;

    public User(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
        reservations=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addReservation(Reservation reservation)
    {
        reservations.add(reservation);
    }
    public void deleteReservations(Reservation reservation)
    {
        reservations.remove(reservation);
    }


}
