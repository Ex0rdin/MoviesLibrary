package ua.exordin.movies.util;

import java.sql.Date;

public interface TestConstants {

    //=== Common

    String REMOTE_ADDR = "qwe.rty.0.1";
    String USER_AGENT_VALUE = "Big Funny Browser";

    //=== Movie section

    long ID_1 = 1L;
    String NAME_1 = "Test Name 1";
    String DESCRIPTION_1 = "Test Description 1";
    Date PREMIER_DATE_1 = UsingThisTo.getCurrentDate();
    int DURATION_IN_MINUTES_1 = 5000;
    int BUDGET_IN_DOLLARS_1 = 223456662;
    float RATING_1 = 3.7f;

    long ID_2 = 1L;
    String NAME_2 = "Test Name 2";
    String DESCRIPTION_2 = "Test Description 2";
    Date PREMIER_DATE_2 = UsingThisTo.getCurrentDate();
    int DURATION_IN_MINUTES_2 = 3400;
    int BUDGET_IN_DOLLARS_2 = 22343462;
    float RATING_2 = 1.7f;

    long ID_3 = 1L;
    String NAME_3 = "Test Name 3";
    String DESCRIPTION_3 = "Test Description 3";
    Date PREMIER_DATE_3 = UsingThisTo.getCurrentDate();
    int DURATION_IN_MINUTES_3 = 3490;
    int BUDGET_IN_DOLLARS_3 = 22343002;
    float RATING_3 = 1.0f;

}
