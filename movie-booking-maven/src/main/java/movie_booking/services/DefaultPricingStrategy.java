package movie_booking.services;

import movie_booking.models.*;
public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Seat seat, ShowTime showTime) {
        return seat.getBasePrice();
    }
}