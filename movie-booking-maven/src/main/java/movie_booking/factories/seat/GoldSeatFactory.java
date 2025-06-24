package movie_booking.factories;

import movie_booking.models.*;

class GoldSeatFactory implements SeatFactory {
    @Override
    public Seat create(String row, int number) {
        return new Gold(row, number);
    }
}