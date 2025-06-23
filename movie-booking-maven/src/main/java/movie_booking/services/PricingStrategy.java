package movie_booking.services;

import movie_booking.models.*;
public interface PricingStrategy {
    double calculatePrice(Seat seat, ShowTime showTime);
}