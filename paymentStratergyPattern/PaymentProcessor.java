package paymentStratergyPattern;

public class PaymentProcessor {

    public boolean processPayment(double amount, PaymentStratergy paymentStrategy) {
        paymentStrategy.processPayment(amount);
        return true; // Assume payment is successful for simplicity
    }
}
