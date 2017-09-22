package ua.exordin.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"ua.exordin.movies"})
public class MoviesApp {
    public static void main(String[] args) {
        SpringApplication.run(MoviesApp.class, args);
    }
}
