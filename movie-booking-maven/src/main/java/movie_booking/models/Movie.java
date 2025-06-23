package movie_booking.models;

import lombok.*;
import java.util.UUID;
@Getter
public class Movie implements ShowItem {
    String id = UUID.randomUUID().toString();
    String name;
    String genre;

    public Movie(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }
}