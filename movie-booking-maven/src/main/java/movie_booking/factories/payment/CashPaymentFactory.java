package movie_booking.factories;

import movie_booking.models.CashPayment;

public class CashPaymentFactory implements PaymentFactory {
    @Override
    public CashPayment create(double amount) {
        return new CashPayment(amount);
    }
}
