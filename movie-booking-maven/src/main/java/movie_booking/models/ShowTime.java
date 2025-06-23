package movie_booking.models;

import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;
public class ShowTime {
    String id = UUID.randomUUID().toString();
    @Getter
    Hall hall;
    @Getter
    ShowItem showItem;
    @Getter
    private LocalDateTime startTime;

    public ShowTime(Hall hall, ShowItem showItem, LocalDateTime startTime) {
        this.hall = hall;
        this.showItem = showItem;
        this.startTime = startTime;
    }
}
