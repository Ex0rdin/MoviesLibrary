package ua.exordin.movies.service;

import ua.exordin.movies.model.Movie;

import java.sql.Date;
import java.util.List;

public interface MovieService {

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(long id);

    Movie findMovie(String name, Date premierDate, int durationInMinutes);

    List<Movie> findAllMovies();

    void deleteAllMovies();

    boolean isMovieExist(Movie movie);

}
