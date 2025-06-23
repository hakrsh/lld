package movie_booking.models;

import java.util.*;
import movie_booking.enums.*;

public class Ticket {
    String id = UUID.randomUUID().toString();
    Seat seat;
    TicketStatus status;
    ShowTime show;

    public Ticket(Seat seat, ShowTime show) {
        this.seat = seat;
        this.show = show;
    }

    public void book() {
        this.status = TicketStatus.BOOKED;
        this.seat.book();
    }
}