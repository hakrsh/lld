package movie_booking.models;

import lombok.*;
import java.util.*;
@Getter
public class Cinema {
    String id = UUID.randomUUID().toString();
    String name;
    City city;
    List<Hall> halls;

    public Cinema(String name, City city) {
        this.name = name;
        this.city = city;
        this.halls = new ArrayList<>();
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
    }

    public void removeHall(Hall hall) {
        this.halls.remove(hall);
    }
}