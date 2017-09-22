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


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
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

    //TODO PUT What if name is not unique?



    //TODO DELETE

    //TODO DELETE ALL


    //TODO RATING



}
