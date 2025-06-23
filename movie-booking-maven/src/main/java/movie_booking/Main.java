package movie_booking;

import java.time.LocalDateTime;
import java.util.*;

import movie_booking.services.*;
import movie_booking.models.*;
import movie_booking.enums.SeatType;

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