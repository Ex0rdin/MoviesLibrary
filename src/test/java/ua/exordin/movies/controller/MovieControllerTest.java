package ua.exordin.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.exordin.movies.model.Movie;
import ua.exordin.movies.service.MovieService;
import ua.exordin.movies.service.RateService;
import ua.exordin.movies.service.RequestsLogService;
import ua.exordin.movies.util.Constants;
import ua.exordin.movies.util.TestConstants;
import ua.exordin.movies.util.TestDataBuilder;
import ua.exordin.movies.util.UsingThisTo;

import static org.hamcrest.Matchers.closeTo;
import static ua.exordin.movies.util.Constants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieControllerTest {

    private MockMvc mockMvc;

    private GlobalExceptionController advice;

    private RequestPostProcessor requestPostProcessor;
    private MockHttpServletRequest mockHttpServletRequest;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private MovieService movieService;

    @Mock
    private RateService rateService;

    @Mock
    private RequestsLogService requestsLogService;

    @InjectMocks
    private MovieController movieController;

    @Before
    public void before() {
        advice = new GlobalExceptionController();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).setControllerAdvice(advice).build();
        mockHttpServletRequest = new MockHttpServletRequest();
        requestPostProcessor = (mockHttpServletRequest) -> {
            mockHttpServletRequest.setRemoteAddr("qwe.rty.0.1");
            mockHttpServletRequest.addHeader(Constants.USER_AGENT, "Big Funny Browser");
            return mockHttpServletRequest;
        };
    }

    @Test
    public void saveMovieTest() throws Exception {
        Movie movie = TestDataBuilder.constructSingleMovieEntitity().get(0);

        when(movieService.saveMovie(movie)).thenReturn(movie);

        String jsonedMovie = asJsonString(movie);

        MvcResult mvr = mockMvc.perform(post(CONTROLLER_MAPPING_ROOT + ENTITY + "/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonedMovie)
                .with(requestPostProcessor))
                .andExpect(status().isCreated())
                .andReturn();

        verify(movieService, times(1)).saveMovie(fromJsonString(jsonedMovie));
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void findAllMoviesTest() throws Exception {
        when(movieService.findAllMovies()).thenReturn(TestDataBuilder.constructListOfMovieEntities());
        MvcResult mvr = mockMvc.perform(get(CONTROLLER_MAPPING_ROOT + ENTITY + "/")
                .with(requestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].id", is((int) TestConstants.ID_1)))
                .andExpect(jsonPath("$[0].name", is(TestConstants.NAME_1)))
                .andExpect(jsonPath("$[0].description", is(TestConstants.DESCRIPTION_1)))
                .andExpect(jsonPath("$[0].premierDate", is(TestConstants.PREMIER_DATE_1.toString())))
                .andExpect(jsonPath("$[0].durationInMinutes", is(TestConstants.DURATION_IN_MINUTES_1)))
                .andExpect(jsonPath("$[0].budgetInDollars", is(TestConstants.BUDGET_IN_DOLLARS_1)))
                .andExpect(jsonPath("$[0].rating", closeTo(TestConstants.RATING_1, TestConstants.RATING_1)))

                .andExpect(jsonPath("$[1].id", is((int) TestConstants.ID_2)))
                .andExpect(jsonPath("$[1].name", is(TestConstants.NAME_2)))
                .andExpect(jsonPath("$[1].description", is(TestConstants.DESCRIPTION_2)))
                .andExpect(jsonPath("$[1].premierDate", is(TestConstants.PREMIER_DATE_2.toString())))
                .andExpect(jsonPath("$[1].durationInMinutes", is(TestConstants.DURATION_IN_MINUTES_2)))
                .andExpect(jsonPath("$[1].budgetInDollars", is(TestConstants.BUDGET_IN_DOLLARS_2)))
                .andExpect(jsonPath("$[1].rating", closeTo(TestConstants.RATING_2, TestConstants.RATING_2)))

                .andExpect(jsonPath("$[2].id", is((int) TestConstants.ID_3)))
                .andExpect(jsonPath("$[2].name", is(TestConstants.NAME_3)))
                .andExpect(jsonPath("$[2].description", is(TestConstants.DESCRIPTION_3)))
                .andExpect(jsonPath("$[2].premierDate", is(TestConstants.PREMIER_DATE_3.toString())))
                .andExpect(jsonPath("$[2].durationInMinutes", is(TestConstants.DURATION_IN_MINUTES_3)))
                .andExpect(jsonPath("$[2].budgetInDollars", is(TestConstants.BUDGET_IN_DOLLARS_3)))
                .andExpect(jsonPath("$[2].rating", closeTo(TestConstants.RATING_3, TestConstants.RATING_3)))

                .andReturn();
        verify(movieService, times(1)).findAllMovies();
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Movie fromJsonString(String inputString) {
        try {
            return objectMapper.readValue(inputString, Movie.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    private long id;
    private String name;
    private String description;
    private Date premierDate;
    private int durationInMinutes;
    private int budgetInDollars;
    private float rating;
     */
}
