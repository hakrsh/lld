package movie_booking.models;

public class Gold extends Seat {
    public Gold(String row, Integer number) {
        super(row, number);
        this.setBasePrice(200);
    }
}