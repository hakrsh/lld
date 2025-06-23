package movie_booking.models;

import movie_booking.enums.*;
import lombok.*;

public abstract class Seat {
    String row;
    int number;
    SeatStatus status = SeatStatus.AVAILABLE;
    @Getter
    @Setter
    double basePrice;

    Seat(String row, int number) {
        this.row = row;
        this.number = number;
    }

    public String getName() {
        return this.row + this.number;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    public void book() {
        this.status = SeatStatus.BOOKED;
    }

    public void lock() {
        this.status = SeatStatus.LOCKED;
    }

    public void unlock() {
        this.status = SeatStatus.AVAILABLE;
    }
}