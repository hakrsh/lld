package movie_booking.models;

public class Silver extends Seat {
    public Silver(String row, Integer number) {
        super(row, number);
        this.setBasePrice(150);
    }
}