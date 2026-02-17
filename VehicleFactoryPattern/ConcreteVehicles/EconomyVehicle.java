package VehicleFactoryPattern.ConcreteVehicles;

import CommonEnums.VehicleEnums.VehicleType;
import VehicleFactoryPattern.Vehicle;

public class EconomyVehicle extends Vehicle {

    private static final double RATE_MULTIPLIER=1.0;

    public EconomyVehicle(String registerationNumber, String model, VehicleType type, double baseRentalPrice) {
        super(registerationNumber, model, type, baseRentalPrice);
    }

    @Override
    public double calculateRentalFee(int days)
    {
        return getBaseRentalPrice()*days*RATE_MULTIPLIER;
    }
}
