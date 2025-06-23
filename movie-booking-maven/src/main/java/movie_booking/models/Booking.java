package movie_booking.models;

import lombok.*;
import java.util.*;

public class Booking {
    @Getter
    String id = UUID.randomUUID().toString();
    Customer customer;
    ShowTime show;
    List<Ticket> tickets;
    Payment payment;

    public Booking(Customer customer, ShowTime show, List<Ticket> tickets, Payment payment) {
        this.customer = customer;
        this.show = show;
        this.tickets = tickets;
        this.payment = payment;
    }
}