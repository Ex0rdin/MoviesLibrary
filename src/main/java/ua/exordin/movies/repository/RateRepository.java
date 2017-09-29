package ua.exordin.movies.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.exordin.movies.model.Rate;

public interface RateRepository extends CrudRepository<Rate, Long> {

    @Query("from Rate where movie_id=:movie_id")
    Iterable<Rate> findAllByMovieId(@Param("movie_id") long movieId);

}
