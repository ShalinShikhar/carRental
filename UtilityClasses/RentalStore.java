package UtilityClasses;

import CommonEnums.VehicleEnums.VehicleStatus;
import VehicleFactoryPattern.Vehicle;

import java.util.*;

public class RentalStore {
    private int id;
    private String name;
    private Location location;
    private Map<String, Vehicle> vehicles;

    public RentalStore(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.vehicles = new HashMap<>();
    }

    public List<Vehicle> getAvailableVehicles(Date startDate, Date endDate)
    {
        List<Vehicle> availableVehicle=new ArrayList<>();
        for(Vehicle vehicle : vehicles.values())
        {
            if(vehicle.getStatus()== VehicleStatus.AVAILABLE)
            {
                availableVehicle.add(vehicle);
            }
        }
        return availableVehicle;
    }
    public void addVehicle(Vehicle vehicle)
    {
        vehicles.put(vehicle.getRegisterationNumber(),vehicle);
    }
    public void removeVehicle(String registrationNumber) {
        vehicles.remove(registrationNumber);
    }
    public boolean isVehicleAvailable(String registerationNumber,Date startDate,Date endDate)
    {
        Vehicle vehicle=vehicles.get(registerationNumber);
        return vehicle!=null && vehicle.getStatus()==VehicleStatus.AVAILABLE;
        //can be modified and check if the vehicle is available frm start to end
    }
    public Vehicle getVehicle(String registerationNumber)
    {
        return vehicles.get(registerationNumber);
    }
    public Map<String,Vehicle> getAllVehicle()
    {
        return vehicles;
    }
    public int getId()
    {
        return id;
    }

}
