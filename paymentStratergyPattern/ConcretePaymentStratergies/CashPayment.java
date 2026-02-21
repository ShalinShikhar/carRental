package paymentStratergyPattern.ConcretePaymentStratergies;

import paymentStratergyPattern.PaymentStratergy;

public class CashPayment implements PaymentStratergy {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of $ " + amount);
    }
}
