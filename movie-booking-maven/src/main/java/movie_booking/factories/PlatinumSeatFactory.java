package movie_booking.factories;

import movie_booking.models.*;

class PlatinumSeatFactory implements SeatFactory {
    @Override
    public Seat create(String row, int number) {
        return new Platinum(row, number);
    }
}