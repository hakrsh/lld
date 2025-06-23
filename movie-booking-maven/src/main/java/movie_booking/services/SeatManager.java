package movie_booking.services;

import movie_booking.models.*;
import movie_booking.enums.*;
import movie_booking.factories.SeatFactoryRegistry;

public class SeatManager {
    public void addSeats(Hall hall, SeatType type, String row, int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            Seat seat = SeatFactoryRegistry.createSeat(type, row, i);
            hall.addSeat(seat);
        }
    }

    public void removeSeat(Hall hall, Seat seat) {
        hall.removeSeat(seat);
    }
}