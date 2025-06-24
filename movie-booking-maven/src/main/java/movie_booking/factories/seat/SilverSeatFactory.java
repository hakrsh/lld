package movie_booking.factories;

import movie_booking.models.*;

class SilverSeatFactory implements SeatFactory {
    @Override
    public Seat create(String row, int number) {
        return new Silver(row, number);
    }
}