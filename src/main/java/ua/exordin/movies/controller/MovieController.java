package ua.exordin.movies.controller;

import com.sun.jndi.toolkit.url.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.service.MovieService;
import ua.exordin.movies.util.Constants;

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
     * @return Spring response entity on succesfully created or already present Movie Entity
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = Constants.ENTITY + "/", method = RequestMethod.POST)
    public ResponseEntity<?> createMovie(@RequestBody Movie movie, UriComponentsBuilder compBuilder) {
        logger.info("Creating new Movie record : {}", movie);

        ResponseEntity responseEntity = null;

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
    //TODO GET

    //TODO PUT

    //TODO DELETE

    //TODO RATING

}
