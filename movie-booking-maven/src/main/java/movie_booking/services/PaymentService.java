package movie_booking.services;

import movie_booking.models.*;
import movie_booking.enums.*;
public class PaymentService {
    public Payment makePayment(double amount) {
        Payment p = new Payment(amount);
        p.setStatus(PaymentStatus.SUCCESS);
        return p;
    }
}