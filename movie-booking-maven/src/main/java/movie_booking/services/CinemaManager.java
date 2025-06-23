package movie_booking.services;

import java.util.*;
import movie_booking.models.*;

public class CinemaManager {
    private final Map<String, Cinema> cinemas = new HashMap<>();

    public Cinema addCinema(String name, City city) throws Exception {
        String key = name.toLowerCase();
        if (cinemas.containsKey(key)) {
            throw new Exception("Cinema already exists with name: " + name);
        }
        Cinema cinema = new Cinema(name, city);
        cinemas.put(key, cinema);
        return cinema;
    }

    public void removeCinema(String name) throws Exception {
        String key = name.toLowerCase();
        if (!cinemas.containsKey(key)) {
            throw new Exception("Cinema not found: " + name);
        }
        cinemas.remove(key);
    }

    public List<Cinema> getCinemasByCity(City city) {
        return cinemas.values().stream()
                .filter(c -> c.getCity().equals(city))
                .toList();
    }

    public Cinema getCinemaByName(String name) throws Exception {
        String key = name.toLowerCase();
        Cinema cinema = cinemas.get(key);
        if (cinema == null)
            throw new Exception("Cinema not found: " + name);
        return cinema;
    }
}