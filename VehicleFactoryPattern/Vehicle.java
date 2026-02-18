package VehicleFactoryPattern;

import CommonEnums.ReservationEnums.ReservationStatus;
import CommonEnums.VehicleEnums.VehicleStatus;
import CommonEnums.VehicleEnums.VehicleType;

public abstract class Vehicle {

    private String registerationNumber;
    private String model;
    private VehicleType type;
    private VehicleStatus status;
    private double baseRentalPrice;

    public Vehicle(String registerationNumber, String model, VehicleType type, double baseRentalPrice) {
        this.registerationNumber = registerationNumber;
        this.model = model;
        this.type = type;
        this.baseRentalPrice = baseRentalPrice;
        this.status= VehicleStatus.AVAILABLE;
    }

    // Abstract method for calculating rental fee
    public abstract double calculateRentalFee(int days);

    public String getRegisterationNumber() {
        return registerationNumber;
    }

    public void setRegisterationNumber(String registerationNumber) {
        this.registerationNumber = registerationNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public void setBaseRentalPrice(double baseRentalPrice) {
        this.baseRentalPrice = baseRentalPrice;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getType() {
        return type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public double getBaseRentalPrice() {
        return baseRentalPrice;
    }




}
