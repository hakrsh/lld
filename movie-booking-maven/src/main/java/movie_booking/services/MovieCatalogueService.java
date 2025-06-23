package movie_booking.services;

import movie_booking.models.*;
import java.util.*;
public class MovieCatalogueService {
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