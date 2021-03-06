package ua.exordin.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;
import ua.exordin.movies.repository.MovieRepository;
import ua.exordin.movies.repository.RateRepository;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class RateServiceImpl implements RateService{

    @Autowired
    RateRepository rateRepository;

    @Autowired
    MovieRepository movieRepository;

    @Transactional
    @Override
    public void rateMovie(Rate rate, Movie movie) {
        if (0 > rate.getMark() || rate.getMark() > 5) {
            throw new ConstraintViolationException("Mark is out of range. Must be from 0 to 5", null);
        }
        rateRepository.save(rate);
        List<Rate> rates = (List<Rate>) rateRepository.findAllByMovieId(rate.getMovieId());
        float averageRate = (float) rates.stream().map(Rate::getMark).mapToInt(Integer::intValue).summaryStatistics().getAverage();
        movie.setRating(averageRate);
        movieRepository.save(movie);
    }

}
