package paymentStratergyPattern.ConcretePaymentStratergies;

import paymentStratergyPattern.PaymentStratergy;

public class CreditCardPayment implements PaymentStratergy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $"+amount);
    }
}
