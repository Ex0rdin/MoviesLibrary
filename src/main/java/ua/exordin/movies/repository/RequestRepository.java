package ua.exordin.movies.repository;

import org.springframework.data.repository.CrudRepository;
import ua.exordin.movies.model.Request;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
