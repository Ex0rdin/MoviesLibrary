package ua.exordin.movies.service;

import ua.exordin.movies.model.Movie;

import java.util.List;

public interface MovieService {

    Movie saveMovie(Movie movie);

    Movie updateMovie(Movie movie);

    void deleteMovie(long id);

    Movie findMovie(long id);

    List<Movie> findAllMovies();

    void deleteAllMovies();
}
