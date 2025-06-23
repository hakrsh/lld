package movie_booking.services;

import movie_booking.models.*;;
public class DemandBasedPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Seat seat, ShowTime showTime) {
        double baseRate = seat.getBasePrice();
        Hall hall = showTime.getHall();
        long availableSeats = hall.getSeats().stream().filter(s -> s.isAvailable()).count();
        long totalSeats = hall.getSeats().size();
        if (availableSeats < totalSeats * 0.3) {
            return baseRate * 1.15;
        }
        return baseRate * 0.9;
    }
}