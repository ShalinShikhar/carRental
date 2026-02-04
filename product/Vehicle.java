package product;

public class Vehicle {
    private final int vehicleID;
    private final String vehicleNumber;
    private final VehicleType vehicleType;
    private volatile VehicleStatus vehicleStatus;
    private double dailyRentalCost;

    public Vehicle(int vehicleID, String vehicleNumber, VehicleType vehicleType) {
        this.vehicleID = vehicleID;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleStatus=VehicleStatus.AVAILABLE;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public double getDailyRentalCost() {
        return dailyRentalCost;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public void setDailyRentalCost(double dailyRentalCost) {
        this.dailyRentalCost = dailyRentalCost;
    }
}
