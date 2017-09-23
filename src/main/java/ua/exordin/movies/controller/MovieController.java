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

        //TODO Make Unique constraint in db ?
        if (movieService.isMovieExist(movie)) {
            logger.error("Movie record {} already exists", movie.getName());

            responseEntity = new ResponseEntity(String.format("Movie record %s already exists", movie.getName()), HttpStatus.CONFLICT);
        } else {
            movieService.saveMovie(movie);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(compBuilder.path(Constants.CONTROLLER_MAPPING_ROOT + Constants.ENTITY + "/{name}")
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

        //TODO How to perform selective request according to passed parameters ?
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
            responseEntity = new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
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
            foundMovie.setRating(movie.getRating()); //???????

            movieService.updateMovie(foundMovie);
            responseEntity = new ResponseEntity<Movie>(foundMovie, HttpStatus.OK);
        }

        return responseEntity;
    }


    //TODO DELETE

    //TODO DELETE ALL


    //TODO RATING

}
