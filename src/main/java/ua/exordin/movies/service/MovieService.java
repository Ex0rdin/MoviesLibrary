package ua.exordin.movies.service;

import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;

import java.util.List;

public interface MovieService {

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(long id);

    Movie findMovie(long id);

    List<Movie> findAllMovies();

    void deleteAllMovies();

    boolean isMovieExist(Movie movie);

    void rateMovie(Rate rate);

    float getMovieRating(long id);

}
