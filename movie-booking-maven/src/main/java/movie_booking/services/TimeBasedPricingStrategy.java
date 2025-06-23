package movie_booking.services;

import movie_booking.models.*;

public class TimeBasedPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Seat seat, ShowTime showTime) {
        double baseRate = seat.getBasePrice();
        int hour = showTime.getStartTime().getHour();
        if (hour > 18 && hour < 23)
            return 1.10 * baseRate;
        if (hour < 12)
            return 0.90 * baseRate;
        return baseRate;
    }
}