package movie_booking.factories;

import movie_booking.models.Payment;

public interface PaymentFactory {
    Payment create(double amount);
}
