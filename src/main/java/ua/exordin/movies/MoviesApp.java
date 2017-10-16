package ua.exordin.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"ua.exordin.movies"})
@EnableJpaRepositories(basePackages = {"ua.exordin.movies.repository"})
public class MoviesApp {
    public static void main(String[] args) {
        SpringApplication.run(MoviesApp.class, args);
    }
}

// Another one comment
