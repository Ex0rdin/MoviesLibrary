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

import java.sql.Date;

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

            responseEntity = new ResponseEntity(String.format("Movie record {} already exists", movie.getName()), HttpStatus.CONFLICT);
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
     * @param name Movie name
     * @param premierDate date of Movie release
     * @param durationInMinutes Movie duration
     * @return Spring response entity on successfully fetched or absent Movie entity
     */


    //TODO Narrow search criteria, since there could be more than one movie with such name ?
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovie(@PathVariable("name") String name,
                                      @RequestParam(value="premierDate", required = false) Date premierDate,
                                      @RequestParam(value="durationInMinutes", required = false) int durationInMinutes) {
        logger.info("Getting Movie {}", name);

        ResponseEntity responseEntity = null;

        //TODO How to perform selective request according to passed parameters ?
        Movie movie = movieService.findMovie(name, premierDate, durationInMinutes);

        //TODO Use Optional?
        if (movie == null) {
            logger.error("Movie name: {}, premierDate: {}, durationInMinutes: {}, not found",
                    name, premierDate, durationInMinutes);
            responseEntity = new ResponseEntity(String.format("Movie name: {}, premierDate: {}, durationInMinutes: {}, not found",
                    name, premierDate, durationInMinutes), HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.OK);
        }

        return responseEntity;
    }

    //TODO GET ALL

    //TODO PUT

    //TODO DELETE

    //TODO RATING

}
