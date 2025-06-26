package movie_booking.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Getter
public class ShowTime {
    String id = UUID.randomUUID().toString();    
    Hall hall;
    ShowItem showItem;
    private LocalDateTime startTime;
    private List<Booking> bookings = new ArrayList<>();

    public ShowTime(Hall hall, ShowItem showItem, LocalDateTime startTime) {
        this.hall = hall;
        this.showItem = showItem;
        this.startTime = startTime;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }
}
