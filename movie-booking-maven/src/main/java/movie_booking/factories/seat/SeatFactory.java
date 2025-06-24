package movie_booking.factories;

import movie_booking.models.*;

public interface SeatFactory {
    Seat create(String row, int number);
}