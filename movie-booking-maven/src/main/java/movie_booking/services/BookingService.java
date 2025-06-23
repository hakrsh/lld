package movie_booking.services;

import movie_booking.models.*;
import java.util.*;;

public class BookingService {
    PricingStrategy pricingStrategy;
    PaymentService paymentService;

    public BookingService(PricingStrategy pricingStrategy, PaymentService paymentService) {
        this.pricingStrategy = pricingStrategy;
        this.paymentService = paymentService;
    }

    public Booking bookSeats(Customer customer, ShowTime show, List<Seat> seats) throws Exception {
        synchronized (this) {
            List<Ticket> tickets = new ArrayList<>();
            double total = 0;
            for (Seat seat : seats) {
                if (!seat.isAvailable()) {
                    throw new Exception("Seat " + seat.getName() + " is already booked");
                }
            }
            for (Seat seat : seats) {
                seat.lock();
                tickets.add(new Ticket(seat, show));
                total += this.pricingStrategy.calculatePrice(seat, show);
            }
            Payment payment = paymentService.makePayment(total);
            if (!payment.isSuccess()) {
                for (Seat seat : seats) {
                    seat.unlock();
                }
                throw new Exception("Payment failed");
            }
            for (Ticket ticket : tickets) {
                ticket.book();
            }
            Booking booking = new Booking(customer, show, tickets, payment);
            System.out.println("Booking is confirmed for " + customer.getName() + " with total amount: " + total);
            return booking;
        }
    }
}