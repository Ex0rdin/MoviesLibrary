package ua.exordin.movies.service;

import org.springframework.stereotype.Service;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;

import java.util.List;

@Service
public class RateServiceImpl implements RateService{

    @Override
    public void rateMovie(Rate rate) {

    }

    @Override
    public List<Rate> getMovieRatings(long id) {
        return null;
    }

    @Override
    public void recalculateAvgRateFor(Movie movie) {

    }
}
