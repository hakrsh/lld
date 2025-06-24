package movie_booking.models;

import lombok.*;
import java.util.UUID;
abstract class Person {
    String id = UUID.randomUUID().toString();
    @Getter
    String name;

    Person(String name) {
        this.name = name;
    }
}