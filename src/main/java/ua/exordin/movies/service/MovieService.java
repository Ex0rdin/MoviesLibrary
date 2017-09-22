package ua.exordin.movies.service;

import ua.exordin.movies.model.Movie;

import java.util.List;

public interface MovieService {

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(long id);

    List<Movie> findAllMovies();

    void deleteAllMovies();

    boolean isMovieExist(Movie movie);

}
