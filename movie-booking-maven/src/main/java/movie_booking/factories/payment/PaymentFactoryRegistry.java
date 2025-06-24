package movie_booking.factories;

import movie_booking.enums.PaymentMethod;
import movie_booking.factories.CardPaymentFactory;
import movie_booking.factories.CashPaymentFactory;
import movie_booking.models.Payment;

import java.util.*;

public class PaymentFactoryRegistry {
    private static Map<PaymentMethod, PaymentFactory> factoryMap = new HashMap<>();
    static {
        factoryMap.put(PaymentMethod.CASH, new CashPaymentFactory());
        factoryMap.put(PaymentMethod.CARD, new CardPaymentFactory());
    }

    public static Payment createPayment(PaymentMethod paymentMethod, double amount) throws Exception {
        PaymentFactory factory = factoryMap.get(paymentMethod);
        if (factory == null) {
            throw new Exception("Unkonwn payment type");
        }
        return factory.create(amount);
    }
}
