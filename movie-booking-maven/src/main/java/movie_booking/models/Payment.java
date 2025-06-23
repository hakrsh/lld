package movie_booking.models;

import lombok.*;

import java.time.LocalDateTime;
import movie_booking.enums.*;

@Getter
public class Payment {
    double amount;
    LocalDateTime paidAt = LocalDateTime.now();
    @Setter
    PaymentStatus status;

    public Payment(double amount) {
        this.amount = amount;
    }

    public boolean isSuccess() {
        return this.status == PaymentStatus.SUCCESS;
    }
}