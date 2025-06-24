package movie_booking.factories;

import movie_booking.factories.PaymentFactory;
import movie_booking.models.CardPayment;
public class CardPaymentFactory implements PaymentFactory{
    @Override
    public CardPayment create(double amount) {
        return new CardPayment(amount);
    }
}
