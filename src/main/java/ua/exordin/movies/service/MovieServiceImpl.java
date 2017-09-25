package ua.exordin.movies.service;

import org.springframework.stereotype.Service;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;

import java.util.List;

@Service("userService")
public class MovieServiceImpl implements MovieService {

    @Override
    public void saveMovie(Movie movie) {

    }

    @Override
    public void updateMovie(Movie movie) {

    }

    @Override
    public void deleteMovie(long id) {

    }

    @Override
    public Movie findMovie(long id) {
        return null;
    }

    @Override
    public List<Movie> findAllMovies() {
        return null;
    }

    @Override
    public void deleteAllMovies() {

    }

    @Override
    public boolean isMovieExist(Movie movie) {
        return false;
    }

    @Override
    public void rateMovie(Rate rate) {

    }

    @Override
    public float getMovieRating(long id) {
        return 0;
    }
}
