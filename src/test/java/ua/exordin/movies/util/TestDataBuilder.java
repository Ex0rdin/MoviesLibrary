package ua.exordin.movies.util;

import ua.exordin.movies.model.Movie;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ua.exordin.movies.util.TestConstants.*;

public class TestDataBuilder {

    /*
    private long id;
    private String name;
    private String description;
    private Date premierDate;
    private int durationInMinutes;
    private int budgetInDollars;
    private float rating;
     */

    public static List<Movie> constructListOfMovieEntities() {
        return  Arrays.asList(
                new Movie(ID_1, NAME_1, DESCRIPTION_1, PREMIER_DATE_1, DURATION_IN_MINUTES_1, BUDGET_IN_DOLLARS_1, RATING_1),
                new Movie(ID_2, NAME_2, DESCRIPTION_2, PREMIER_DATE_2, DURATION_IN_MINUTES_2, BUDGET_IN_DOLLARS_2, RATING_2),
                new Movie(ID_3, NAME_3, DESCRIPTION_3, PREMIER_DATE_3, DURATION_IN_MINUTES_3, BUDGET_IN_DOLLARS_3, RATING_3)
        );
    }


    public static List<Movie> constructSingleMovieEntitity() {
        return Collections.singletonList(
                new Movie(ID_1, NAME_1, DESCRIPTION_1, PREMIER_DATE_1, DURATION_IN_MINUTES_1, BUDGET_IN_DOLLARS_1, RATING_1)
        );
    }
}
