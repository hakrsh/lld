package movie_booking.models;

public class Platinum extends Seat {
    public Platinum(String row, Integer number) {
        super(row, number);
        this.setBasePrice(250);
    }
}