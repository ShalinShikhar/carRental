package payment;

public interface PaymentStratergy {

    Payment processPayment(Bill bill,double paymentAmount);
}
