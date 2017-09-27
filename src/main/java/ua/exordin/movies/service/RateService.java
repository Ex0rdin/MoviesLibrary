package ua.exordin.movies.service;

import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;

import java.util.List;

public interface RateService {

    void rateMovie(Rate rate);

    List<Rate> getMovieRatings(long id);

    void recalculateAvgRateFor(Movie movie);
}
