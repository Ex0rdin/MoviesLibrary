package ua.exordin.movies.repository;

import org.springframework.data.repository.CrudRepository;
import ua.exordin.movies.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {

}
