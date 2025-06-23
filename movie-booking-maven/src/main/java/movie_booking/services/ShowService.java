package movie_booking.services;

import movie_booking.models.*;

import java.time.LocalDateTime;
import java.util.*;

public class ShowService {
    CinemaManager cinemaManager;

    public ShowService(CinemaManager cinemaManager) {
        this.cinemaManager = cinemaManager;
    }

    public List<ShowTime> getShows(City city, ShowItem showItem) {
        List<ShowTime> shows = new ArrayList<>();
        List<Cinema> cinemas = this.cinemaManager.getCinemasByCity(city);
        for (Cinema cinema : cinemas) {
            for (Hall hall : cinema.getHalls()) {
                for (ShowTime show : hall.getShows()) {
                    if (show.getShowItem().equals(showItem)) {
                        shows.add(show);
                    }
                }
            }
        }
        return shows;
    }

    public void addShow(Hall hall, ShowItem showItem, LocalDateTime startTime) {
        // todo make sure no show is booked
        hall.getShows().add(new ShowTime(hall, showItem, startTime));
    }

    public List<Seat> getAvaialbleSeats(ShowTime showTime) {
        return showTime.getHall().getSeats().stream().filter(seat -> seat.isAvailable()).toList();
    }
}