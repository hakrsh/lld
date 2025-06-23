package movie_booking.models;

import lombok.*;
import java.util.*;
@Getter
public class Hall {
    String id = UUID.randomUUID().toString();
    String name;
    List<Seat> seats = new ArrayList<>();
    List<ShowTime> shows = new ArrayList<>();

    public Hall(String name) {
        this.name = name;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public void removeSeat(Seat seat) {
        this.seats.remove(seat);
    }

    public void addShow(ShowTime show) {
        this.shows.add(show);
    }

    public void removeShow(ShowTime show) {
        this.shows.remove(show);
    }
}
