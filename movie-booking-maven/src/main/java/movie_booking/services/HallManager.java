package movie_booking.services;

import java.util.*;
import movie_booking.models.*;

public class HallManager {
    private final Map<String, Map<String, Hall>> cinemaHalls = new HashMap<>();

    public void registerCinema(Cinema cinema) {
        cinemaHalls.put(cinema.getId(), new HashMap<>());
    }

    public Hall addHall(Cinema cinema, String hallName) throws Exception {
        Map<String, Hall> halls = getHallsMap(cinema);
        String key = hallName.toLowerCase();
        if (halls.containsKey(key)) {
            throw new Exception("Hall already exists: " + hallName);
        }
        Hall hall = new Hall(hallName);
        halls.put(key, hall);
        cinema.addHall(hall);
        return hall;
    }

    public void removeHall(Cinema cinema, Hall hall) throws Exception {
        Map<String, Hall> halls = getHallsMap(cinema);
        String key = hall.getName().toLowerCase();
        if (!halls.containsKey(key)) {
            throw new Exception("Hall not found: " + hall.getName());
        }
        halls.remove(key);
        cinema.removeHall(hall);
    }

    public Map<String, Hall> getHallsMap(Cinema cinema) throws Exception {
        Map<String, Hall> halls = cinemaHalls.get(cinema.getId());
        if (halls == null) {
            throw new Exception("Cinema not registered: " + cinema.getName());
        }
        return halls;
    }
}