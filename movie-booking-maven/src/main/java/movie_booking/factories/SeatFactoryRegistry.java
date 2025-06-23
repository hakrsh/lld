package movie_booking.factories;

import movie_booking.enums.SeatType;
import movie_booking.models.Seat;
import java.util.*;

public class SeatFactoryRegistry {
    private static final Map<SeatType, SeatFactory> factorMap = new HashMap<>();
    static {
        factorMap.put(SeatType.SILVER, new SilverSeatFactory());
        factorMap.put(SeatType.GOLD, new GoldSeatFactory());
        factorMap.put(SeatType.PLATINUM, new PlatinumSeatFactory());
    }

    public static Seat createSeat(SeatType seatType, String row, int number) throws Exception {
        SeatFactory factory = factorMap.get(seatType);
        if (factory == null) {
            throw new Exception("Unknown seat type");
        }
        return factory.create(row, number);
    }
}