package ua.exordin.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.repository.MovieRepository;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return saveMovie(movie);
    }

    @Override
    public void deleteMovie(long id) {
        movieRepository.delete(id);
    }

    @Override
    public Movie findMovie(long id) {
        return movieRepository.findOne(id);
    }

    @Override
    public List<Movie> findAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }
}
