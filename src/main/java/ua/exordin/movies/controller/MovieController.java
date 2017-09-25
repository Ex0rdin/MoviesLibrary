package ua.exordin.movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.model.Rate;
import ua.exordin.movies.service.MovieService;
import ua.exordin.movies.util.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.CONTROLLER_MAPPING_ROOT)
public class MovieController {

    public static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    MovieService movieService;


    /**
     * POST Method for creating Movie entities
     * @param movie Movie record
     * @param compBuilder Spring components builder
     * @return Spring response entity on successfully created or already present Movie Entity
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/", method = RequestMethod.POST)
    public ResponseEntity<?> createMovie(@RequestBody Movie movie, UriComponentsBuilder compBuilder) {
        logger.info("Creating new Movie record : {}", movie);

        ResponseEntity responseEntity = null;

        if (movieService.isMovieExist(movie)) {
            logger.error("Movie with id: {} already exists", movie.getId());

            responseEntity = new ResponseEntity(String.format("Movie with id: %d already exists", movie.getId()), HttpStatus.CONFLICT);
        } else {
            movieService.saveMovie(movie);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(compBuilder.path(Constants.CONTROLLER_MAPPING_ROOT + Constants.ENTITY + "/{id}")
                    .buildAndExpand(movie.getName()).toUri());
            responseEntity = new ResponseEntity<String>(httpHeaders, HttpStatus.CREATED);
        }

        return responseEntity;
    }

    /**
     * GET method for getting Movie by name and additional optional paramters
     * @param id Movie id
     * @return Spring response entity on successfully fetched or absent Movie entity
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovie(@PathVariable("id") long id) {
        logger.info("Getting Movie {}", id);

        ResponseEntity responseEntity = null;

        Movie movie = movieService.findMovie(id);

        //TODO Use Optional?
        if (movie == null) {
            logger.error("Movie with id: {}, not found", id);
            responseEntity = new ResponseEntity(String.format("Movie with id: %d, not found", id), HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(movie, HttpStatus.OK);
        }

        return responseEntity;
    }

    /**
     * GET method for collecting all existing Movies
     * @return  Spring response entity on successfully fetched or absent Movies entities
     */
    @RequestMapping(value = Constants.ENTITY + "/", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> listAllMovies() {
        logger.info("Retrieving all movies");

        ResponseEntity responseEntity = null;

        List<Movie> movies = movieService.findAllMovies();
        if (movies.isEmpty()) {
            logger.warn("No movies found");
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
        }

        return responseEntity;
    }

    /**
     * PUT method for updating Movie
     * @param id Movie id
     * @param movie Movie entity
     * @return Spring response entity on successful update or absent Movie entity
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) {
        logger.info("Updating Movie with id: {}", id);

        ResponseEntity responseEntity = null;

        Movie foundMovie = movieService.findMovie(id);

        if (foundMovie == null) {
            logger.error("Failed to update Movie with id: {}. It was not found.", id);
            responseEntity = new ResponseEntity(String.format("Failed to update Movie with id: %s. It was not found.", id),
                    HttpStatus.NOT_FOUND);
        } else {
            foundMovie.setName(movie.getName());
            foundMovie.setDescription(movie.getDescription());
            foundMovie.setPremierDate(movie.getPremierDate());
            foundMovie.setDurationInMinutes(movie.getDurationInMinutes());
            foundMovie.setBudgetInDollars(movie.getBudgetInDollars());

            movieService.updateMovie(foundMovie);
            responseEntity = new ResponseEntity<>(foundMovie, HttpStatus.OK);
        }

        return responseEntity;
    }

    /**
     * DELETE method to delete existing Movie
     * @param id Movie id
     * @return Spring response entity on successful delete or absent Movie entity
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMovie(@PathVariable("id") long id) {
        logger.info("Deleting Movie with id: {}" , id);

        ResponseEntity responseEntity = null;

        Movie movie = movieService.findMovie(id);
        if (movie == null) {
            logger.error("Have not deleted movie with id {}. It does not exist.", id);
            responseEntity = new ResponseEntity(String.format("Have not deleted movie with id %s. It does not exist.", id),
                    HttpStatus.NOT_FOUND);
        } else {
            movieService.deleteMovie(id);
            responseEntity = new ResponseEntity<Movie>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }


    /**
     * DELETE method to delete all existing Movies
     * @return Spring response entity on successful delete of all Movies
     */
    @RequestMapping(value = Constants.ENTITY + "/", method = RequestMethod.DELETE)
    public ResponseEntity<Movie> deleteAllMovies() {
        logger.info("WARNING! Deleting all Movies!");

        movieService.deleteAllMovies();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //TODO RATE MOVIE
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> rateMovie(@PathVariable("id") long id, @RequestBody Rate rate) {
        logger.info("Adding rating for Movie with id: {}", id);

        ResponseEntity responseEntity = null;

        if (movieService.findMovie(id) == null) {
            logger.error("Failed torate Movie with id: {}", id);
            responseEntity = new ResponseEntity(String.format("Failed torate Movie with id: %d", id),
                    HttpStatus.NOT_FOUND);
        } else {
            movieService.rateMovie(rate);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }

        return responseEntity;
    }

    //TODO GET RATING
}
