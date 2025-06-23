package movie_booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import lombok.*;

enum SeatStatus {
    AVAILABLE,
    BOOKED,
    LOCKED
}

enum SeatType {
    SILVER,
    GOLD,
    PLATINUM
}

enum TicketStatus {
    BOOKED,
    CANCELED
}

enum PaymentStatus {
    SUCCESS,
    PENDING,
    FAILED
}

abstract class Seat {
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

class Silver extends Seat {
    public Silver(String row, Integer number) {
        super(row, number);
        this.setBasePrice(150);
    }
}

class Gold extends Seat {
    public Gold(String row, Integer number) {
        super(row, number);
        this.setBasePrice(200);
    }
}

class Platinum extends Seat {
    public Platinum(String row, Integer number) {
        super(row, number);
        this.setBasePrice(250);
    }
}

interface SeatFactory {
    Seat create(String row, int number);
}

class SilverSeatFactory implements SeatFactory {
    @Override
    public Seat create(String row, int number) {
        return new Silver(row, number);
    }
}

class GoldSeatFactory implements SeatFactory {
    @Override
    public Seat create(String row, int number) {
        return new Gold(row, number);
    }
}

class PlatinumSeatFactory implements SeatFactory {
    @Override
    public Seat create(String row, int number) {
        return new Platinum(row, number);
    }
}

class SeatFactoryRegistry {
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

@Getter
class Hall {
    String id = UUID.randomUUID().toString();
    String name;
    List<Seat> seats = new ArrayList<>();
    List<ShowTime> shows = new ArrayList<>();

    public Hall(String name) {
        this.name = name;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public void removeSeat(Seat seat) {
        this.seats.remove(seat);
    }

    public void addShow(ShowTime show) {
        this.shows.add(show);
    }

    public void removeShow(ShowTime show) {
        this.shows.remove(show);
    }
}

interface ShowItem {
    String getId();
}

@Getter
class Movie implements ShowItem {
    @Getter
    String id = UUID.randomUUID().toString();
    String name;
    String genre;

    Movie(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }
}

class ShowTime {
    String id = UUID.randomUUID().toString();
    @Getter
    Hall hall;
    @Getter
    ShowItem showItem;
    @Getter
    private LocalDateTime startTime;

    public ShowTime(Hall hall, ShowItem showItem, LocalDateTime startTime) {
        this.hall = hall;
        this.showItem = showItem;
        this.startTime = startTime;
    }
}

@Getter
class Cinema {
    String id = UUID.randomUUID().toString();
    String name;
    City city;
    List<Hall> halls;

    public Cinema(String name, City city) {
        this.name = name;
        this.city = city;
        this.halls = new ArrayList<>();
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
    }

    public void removeHall(Hall hall) {
        this.halls.remove(hall);
    }
}

@AllArgsConstructor
class City {
    String name;
    String state;
}

class Ticket {
    String id = UUID.randomUUID().toString();
    Seat seat;
    TicketStatus status;
    ShowTime show;

    public Ticket(Seat seat, ShowTime show) {
        this.seat = seat;
        this.show = show;
    }

    public void book() {
        this.status = TicketStatus.BOOKED;
        this.seat.book();
    }
}

class Booking {
    @Getter
    String id = UUID.randomUUID().toString();
    Customer customer;
    ShowTime show;
    List<Ticket> tickets;
    Payment payment;

    Booking(Customer customer, ShowTime show, List<Ticket> tickets, Payment payment) {
        this.customer = customer;
        this.show = show;
        this.tickets = tickets;
        this.payment = payment;
    }
}

@Getter
class Payment {
    double amount;
    LocalDateTime paidAt = LocalDateTime.now();
    @Setter
    PaymentStatus status;

    Payment(double amount) {
        this.amount = amount;
    }

    public boolean isSuccess() {
        return this.status == PaymentStatus.SUCCESS;
    }
}

class CashPayment extends Payment {
    CashPayment(double amount) {
        super(amount);
    }
}

class CardPayment extends Payment {
    CardPayment(double amount) {
        super(amount);
    }
}

class Person {
    String id = UUID.randomUUID().toString();
    @Getter
    String name;

    Person(String name) {
        this.name = name;
    }
}

class Admin extends Person {
    Admin(String name) {
        super(name);
    }
}

class Customer extends Person {
    Customer(String name) {
        super(name);
    }
}

class Agent extends Person {
    Agent(String name) {
        super(name);
    }
}

class BookingService {
    PricingStrategy pricingStrategy;
    PaymentService paymentService;

    public BookingService(PricingStrategy pricingStrategy, PaymentService paymentService) {
        this.pricingStrategy = pricingStrategy;
        this.paymentService = paymentService;
    }

    public Booking bookSeats(Customer customer, ShowTime show, List<Seat> seats) throws Exception {
        synchronized (this) {
            List<Ticket> tickets = new ArrayList<>();
            double total = 0;
            for (Seat seat : seats) {
                if (!seat.isAvailable()) {
                    throw new Exception("Seat " + seat.getName() + " is already booked");
                }
            }
            for (Seat seat : seats) {
                seat.lock();
                tickets.add(new Ticket(seat, show));
                total += this.pricingStrategy.calculatePrice(seat, show);
            }
            Payment payment = paymentService.makePayment(total);
            if (!payment.isSuccess()) {
                for (Seat seat : seats) {
                    seat.unlock();
                }
                throw new Exception("Payment failed");
            }
            for (Ticket ticket : tickets) {
                ticket.book();
            }
            Booking booking = new Booking(customer, show, tickets, payment);
            System.out.println("Booking is confirmed for " + customer.getName() + " with total amount: " + total);
            return booking;
        }
    }
}

interface PricingStrategy {
    double calculatePrice(Seat seat, ShowTime showTime);
}

class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Seat seat, ShowTime showTime) {
        return seat.getBasePrice();
    }
}

class TimeBasedPricingStrategy implements PricingStrategy {
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

class DemandBasedPricingStrategy implements PricingStrategy {
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

class PaymentService {
    public Payment makePayment(double amount) {
        Payment p = new Payment(amount);
        p.setStatus(PaymentStatus.SUCCESS);
        return p;
    }
}

class MovieCatalogueService {
    List<Movie> movies;

    public MovieCatalogueService() {
        this.movies = new ArrayList<>();
    }

    public Movie addMovie(String name, String genre) {
        Movie movie = new Movie(name, genre);
        this.movies.add(movie);
        return movie;
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
    }

    public List<Movie> searchByName(String name) {
        return this.movies.stream().filter(m -> m.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }

    public List<Movie> searchByGenre(String genre) {
        return this.movies.stream().filter(m -> m.getGenre().toLowerCase().contains(genre.toLowerCase())).toList();
    }
}

class ShowService {
    CinemaManager cinemaManager;

    public ShowService(CinemaManager cinemaManager) {
        this.cinemaManager = cinemaManager;
    }

    public List<ShowTime> getShows(City city, ShowItem showItem) {
        List<ShowTime> shows = new ArrayList<>();
        List<Cinema> cinemas = this.cinemaManager.getCinemasByCity(city);
        for (Cinema cinema : cinemas) {
            for (Hall hall : cinema.getHalls()) {
                for (ShowTime show : hall.getShows()) {
                    if (show.getShowItem().equals(showItem)) {
                        shows.add(show);
                    }
                }
            }
        }
        return shows;
    }

    public void addShow(Hall hall, ShowItem showItem, LocalDateTime startTime) {
        // todo make sure no show is booked
        hall.getShows().add(new ShowTime(hall, showItem, startTime));
    }

    public List<Seat> getAvaialbleSeats(ShowTime showTime) {
        return showTime.getHall().getSeats().stream().filter(seat -> seat.isAvailable()).toList();
    }
}

class CinemaManager {
    private final Map<String, Cinema> cinemas = new HashMap<>();

    public Cinema addCinema(String name, City city) throws Exception {
        String key = name.toLowerCase();
        if (cinemas.containsKey(key)) {
            throw new Exception("Cinema already exists with name: " + name);
        }
        Cinema cinema = new Cinema(name, city);
        cinemas.put(key, cinema);
        return cinema;
    }

    public void removeCinema(String name) throws Exception {
        String key = name.toLowerCase();
        if (!cinemas.containsKey(key)) {
            throw new Exception("Cinema not found: " + name);
        }
        cinemas.remove(key);
    }

    public List<Cinema> getCinemasByCity(City city) {
        return cinemas.values().stream()
                .filter(c -> c.getCity().equals(city))
                .toList();
    }

    public Cinema getCinemaByName(String name) throws Exception {
        String key = name.toLowerCase();
        Cinema cinema = cinemas.get(key);
        if (cinema == null)
            throw new Exception("Cinema not found: " + name);
        return cinema;
    }
}

// HallManager.java
class HallManager {
    private final Map<String, Map<String, Hall>> cinemaHalls = new HashMap<>();

    public void registerCinema(Cinema cinema) {
        cinemaHalls.put(cinema.getId(), new HashMap<>());
    }

    public Hall addHall(Cinema cinema, String hallName) throws Exception {
        Map<String, Hall> halls = getHallsMap(cinema);
        String key = hallName.toLowerCase();
        if (halls.containsKey(key)) {
            throw new Exception("Hall already exists: " + hallName);
        }
        Hall hall = new Hall(hallName);
        halls.put(key, hall);
        cinema.addHall(hall);
        return hall;
    }

    public void removeHall(Cinema cinema, Hall hall) throws Exception {
        Map<String, Hall> halls = getHallsMap(cinema);
        String key = hall.getName().toLowerCase();
        if (!halls.containsKey(key)) {
            throw new Exception("Hall not found: " + hall.getName());
        }
        halls.remove(key);
        cinema.removeHall(hall);
    }

    public Map<String, Hall> getHallsMap(Cinema cinema) throws Exception {
        Map<String, Hall> halls = cinemaHalls.get(cinema.getId());
        if (halls == null) {
            throw new Exception("Cinema not registered: " + cinema.getName());
        }
        return halls;
    }
}

// SeatManager.java
class SeatManager {
    public void addSeats(Hall hall, SeatType type, String row, int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            Seat seat = SeatFactoryRegistry.createSeat(type, row, i);
            hall.addSeat(seat);
        }
    }

    public void removeSeat(Hall hall, Seat seat) {
        hall.removeSeat(seat);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            MovieCatalogueService movieCatalogueService = new MovieCatalogueService();
            CinemaManager cinemaManager = new CinemaManager();
            HallManager hallManager = new HallManager();
            SeatManager seatManager = new SeatManager();
            BookingService bookingService = new BookingService(new TimeBasedPricingStrategy(), new PaymentService());
            ShowService showService = new ShowService(cinemaManager);
            Movie m1 = movieCatalogueService.addMovie("interstellar", "sci-fi");
            Movie m2 = movieCatalogueService.addMovie("rrr", "action");

            City hyd = new City("Hyderabad", "Telangana");
            Cinema c1 = cinemaManager.addCinema("PVR", hyd);
            hallManager.registerCinema(c1);
            Hall h1 = hallManager.addHall(c1, "Audi01");
            seatManager.addSeats(h1, SeatType.SILVER, "A", 15);
            seatManager.addSeats(h1, SeatType.GOLD, "B", 10);
            seatManager.addSeats(h1, SeatType.PLATINUM, "C", 5);

            showService.addShow(h1, m1, LocalDateTime.now());
            showService.addShow(h1, m2, LocalDateTime.now().plusDays(1));

            // User
            while (true) {
                System.out.println("\n--- Movie Booking CLI ---");
                System.out.println("1. Search Movie by Name");
                System.out.println("2. Exit");
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    System.out.print("Enter movie name: ");
                    String movieName = scanner.nextLine();
                    List<Movie> movies = movieCatalogueService.searchByName(movieName);
                    if (movies.isEmpty()) {
                        System.out.println("No movies found.");
                        continue;
                    }

                    for (int i = 0; i < movies.size(); i++) {
                        System.out.println((i + 1) + ". " + movies.get(i).getName());
                    }
                    System.out.print("Select movie index: ");
                    int mIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    Movie selectedMovie = movies.get(mIndex);

                    List<ShowTime> shows = showService.getShows(hyd, selectedMovie);
                    if (shows.isEmpty()) {
                        System.out.println("No shows available.");
                        continue;
                    }

                    for (int i = 0; i < shows.size(); i++) {
                        System.out.println((i + 1) + ". " + shows.get(i).getStartTime());
                    }
                    System.out.print("Select show index: ");
                    int sIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    ShowTime show = shows.get(sIndex);

                    List<Seat> availableSeats = showService.getAvaialbleSeats(show);
                    if (availableSeats.isEmpty()) {
                        System.out.println("No available seats.");
                        continue;
                    }

                    System.out.println("Available Seats:");
                    for (int i = 0; i < availableSeats.size(); i++) {
                        System.out.print((i + 1) + ". " + availableSeats.get(i).getName() + "  ");
                    }
                    System.out.println();
                    System.out.print("Select seat indexes to book (space separated): ");
                    String[] seatIndexes = scanner.nextLine().split(" ");
                    List<Seat> selectedSeats = new ArrayList<>();
                    for (String index : seatIndexes) {
                        int seatIndex = Integer.parseInt(index.trim()) - 1;
                        selectedSeats.add(availableSeats.get(seatIndex));
                    }

                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    Customer customer = new Customer(name);
                    bookingService.bookSeats(customer, show, selectedSeats);
                } else if (choice == 2) {
                    System.out.println("Bie!");
                    break;
                } else {
                    System.out.println("oops..");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}