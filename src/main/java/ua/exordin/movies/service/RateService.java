package ua.exordin.movies.service;

import ua.exordin.movies.model.Rate;

import java.util.List;

public interface RateService {

    //TODO??
    void rateMovie(Rate rate);

    List<Rate> getMovieRatings(long id);
}
