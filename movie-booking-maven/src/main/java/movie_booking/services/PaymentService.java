package movie_booking.services;

import movie_booking.models.*;
import movie_booking.enums.*;
import movie_booking.factories.PaymentFactoryRegistry;
public class PaymentService {
    public Payment makePayment(PaymentMethod PaymentMethod, double amount) throws Exception{
        Payment p = PaymentFactoryRegistry.createPayment(PaymentMethod, amount);
        p.setStatus(PaymentStatus.SUCCESS);
        return p;
    }
}