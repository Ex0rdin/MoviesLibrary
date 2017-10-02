package ua.exordin.movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;
import ua.exordin.movies.service.MovieService;
import ua.exordin.movies.service.RateService;
import ua.exordin.movies.service.RequestsLogService;
import ua.exordin.movies.util.UsingThisTo;
import ua.exordin.movies.util.Constants;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(Constants.CONTROLLER_MAPPING_ROOT)
public class MovieController {

    public static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    MovieService movieService;

    @Autowired
    RateService rateService;

    @Autowired
    RequestsLogService requestsLogService;


    /**
     * POST Method for creating Movie entities
     * @param movie Movie record
     * @return Spring response entity on successfully created or already present Movie Entity
     */
    @PostMapping(Constants.ENTITY + "/")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie, HttpServletRequest httpServletRequest/*UriComponentsBuilder compBuilder*/) {
        logger.info("Creating new Movie record : {}", movie);
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);
        Movie savedMovie = movieService.saveMovie(movie);
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(compBuilder.path(Constants.CONTROLLER_MAPPING_ROOT + Constants.ENTITY + savedMovie.getId())
//                    .buildAndExpand(movie.getName()).toUri());

        requestsLogService.logSuccess(httpServletRequest, dateNow);

        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    /**
     * GET method for getting Movie by name and additional optional paramters
     * @param id Movie id
     * @return Spring response entity on successfully fetched or absent Movie entity
     */
    @SuppressWarnings("unchecked")
    @GetMapping(Constants.ENTITY + "/{id}")
    public ResponseEntity<?> getMovie(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        logger.info("Getting Movie {}", id);
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);

        ResponseEntity responseEntity;

        Movie movie = movieService.findMovie(id);

        if (movie == null) {
            logger.error("Movie with id: {}, not found", id);
            responseEntity = new ResponseEntity(String.format("Movie with id: %d, not found", id), HttpStatus.NOT_FOUND);
            requestsLogService.logFailure(httpServletRequest, dateNow);
        } else {
            responseEntity = new ResponseEntity<>(movie, HttpStatus.OK);
            requestsLogService.logSuccess(httpServletRequest, dateNow);
        }

        return responseEntity;
    }

    /**
     * GET method for collecting all existing Movies
     * @return  Spring response entity on successfully fetched or absent Movies entities
     */
    @GetMapping(Constants.ENTITY + "/")
    public ResponseEntity<List<Movie>> listAllMovies(HttpServletRequest httpServletRequest) {
        logger.info("Retrieving all movies");
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);

        ResponseEntity responseEntity;

        List<Movie> movies = movieService.findAllMovies();
        if (movies.isEmpty()) {
            logger.warn("No movies found");
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            requestsLogService.logFailure(httpServletRequest, dateNow);
        } else {
            responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
            requestsLogService.logSuccess(httpServletRequest, dateNow);
        }

        return responseEntity;
    }

    /**
     * PUT method for updating Movie
     * @param movie Movie entity
     * @return Spring response entity on successful update or absent Movie entity
     */
    @SuppressWarnings("unchecked")
    @PutMapping(Constants.ENTITY + "/")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie, HttpServletRequest httpServletRequest) {
        long movieId = movie.getId();
        logger.info("Updating Movie with id: {}", movieId);
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);

        ResponseEntity responseEntity;

        Movie foundMovie = movieService.findMovie(movieId);

        if (foundMovie == null) {
            logger.error("Failed to update Movie with id: {}. It was not found.", movieId);
            responseEntity = new ResponseEntity(String.format("Failed to update Movie with id: %s. It was not found.", movieId),
                    HttpStatus.NOT_FOUND);
            requestsLogService.logFailure(httpServletRequest, dateNow);
        } else {
            foundMovie.setName(movie.getName());
            foundMovie.setDescription(movie.getDescription());
            foundMovie.setPremierDate(movie.getPremierDate());
            foundMovie.setDurationInMinutes(movie.getDurationInMinutes());
            foundMovie.setBudgetInDollars(movie.getBudgetInDollars());

            movieService.updateMovie(foundMovie);
            responseEntity = new ResponseEntity<>(foundMovie, HttpStatus.OK);
            requestsLogService.logSuccess(httpServletRequest, dateNow);
        }

        return responseEntity;
    }

    /**
     * DELETE method to delete existing Movie
     * @param id Movie id
     * @return Spring response entity on successful delete or absent Movie entity
     */
    @SuppressWarnings("unchecked")
    @DeleteMapping(Constants.ENTITY + "/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        logger.info("Deleting Movie with id: {}" , id);
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);

        ResponseEntity responseEntity;

        Movie movie = movieService.findMovie(id);
        if (movie == null) {
            logger.error("Have not deleted movie with id {}. It does not exist.", id);
            responseEntity = new ResponseEntity(String.format("Have not deleted movie with id %s. It does not exist.", id),
                    HttpStatus.NOT_FOUND);
            requestsLogService.logFailure(httpServletRequest, dateNow);
        } else {
            movieService.deleteMovie(id);
            responseEntity = new ResponseEntity<>(movie, HttpStatus.NO_CONTENT);
            requestsLogService.logSuccess(httpServletRequest, dateNow);
        }

        return responseEntity;
    }


    /**
     * DELETE method to delete all existing Movies
     * @return Spring response entity on successful delete of all Movies
     */
    @DeleteMapping(Constants.ENTITY + "/")
    public ResponseEntity<?> deleteAllMovies(HttpServletRequest httpServletRequest) {
        logger.info("WARNING! Deleting all Movies!");
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);

        movieService.deleteAllMovies();
        requestsLogService.logSuccess(httpServletRequest, dateNow);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * POST Method for adding Movie rates
     * @param id Movie id
     * @param rate Movie rate mark
     * @param httpServletRequest HttpServletRequest
     * @return Spring response entity on successful adding of Movie mark
     */
    @PostMapping(Constants.ENTITY + Constants.RATING + "/{id}")
    public ResponseEntity<?> rateMovie(@PathVariable("id") long id, @RequestParam("rate") int rate,
                                       HttpServletRequest httpServletRequest) {
        logger.info("Adding rating for Movie with id: {}", id);
        Date dateNow = UsingThisTo.getCurrentDate();
        requestsLogService.logReceived(httpServletRequest, dateNow);
        
        ResponseEntity responseEntity;

        Movie foundMovie = movieService.findMovie(id);

        if (foundMovie == null) {
            logger.error("Failed to rate Movie with id: {}. It does not exist.", id);
            responseEntity = new ResponseEntity(String.format("Failed to rate Movie with id: %d. It does not exist.", id),
                    HttpStatus.NOT_FOUND);
            requestsLogService.logFailure(httpServletRequest, dateNow);
        } else {
            Rate currentMovieRate = new Rate();
            currentMovieRate.setIp(UsingThisTo.extractIpFromRequest(httpServletRequest));
            currentMovieRate.setBrowserFingerprint(UsingThisTo.extractUserAgentFromRequest(httpServletRequest));
            currentMovieRate.setMark(rate);
            currentMovieRate.setMovieId(id);

            rateService.rateMovie(currentMovieRate, foundMovie);

            responseEntity = new ResponseEntity<>(foundMovie, HttpStatus.CREATED);
            requestsLogService.logSuccess(httpServletRequest, dateNow);
        }

        return responseEntity;
    }

}
