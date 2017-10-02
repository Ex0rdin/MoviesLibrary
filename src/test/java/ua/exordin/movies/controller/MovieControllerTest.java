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
import static org.junit.Assert.assertNotEquals;
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
            mockHttpServletRequest.setRemoteAddr(TestConstants.REMOTE_ADDR);
            mockHttpServletRequest.addHeader(Constants.USER_AGENT, TestConstants.USER_AGENT_VALUE);
            return mockHttpServletRequest;
        };
    }

    @Test
    public void saveMovieTest() throws Exception {
        Movie movie = TestDataBuilder.constructSingleMovieEntitity1().get(0);

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
        verifyNoMoreInteractions(requestsLogService);
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
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void getMovieTest() throws Exception {
        Movie movie = TestDataBuilder.constructSingleMovieEntitity1().get(0);
        long movieId = movie.getId();

        when(movieService.findMovie(movieId)).thenReturn(movie);

        MvcResult mvr = mockMvc.perform(get(CONTROLLER_MAPPING_ROOT + ENTITY + "/" + movieId)
                .with(requestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$", hasSize(1)))

                .andExpect(jsonPath("id", is((int) TestConstants.ID_1)))
                .andExpect(jsonPath("name", is(TestConstants.NAME_1)))
                .andExpect(jsonPath("description", is(TestConstants.DESCRIPTION_1)))
                .andExpect(jsonPath("premierDate", is(TestConstants.PREMIER_DATE_1.toString())))
                .andExpect(jsonPath("durationInMinutes", is(TestConstants.DURATION_IN_MINUTES_1)))
                .andExpect(jsonPath("budgetInDollars", is(TestConstants.BUDGET_IN_DOLLARS_1)))
                .andExpect(jsonPath("rating", closeTo(TestConstants.RATING_1, TestConstants.RATING_1)))

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void getNonexistentMovieTest() throws Exception {
        long movieId = 1;

        when(movieService.findMovie(movieId)).thenReturn(null);

        MvcResult mvr = mockMvc.perform(get(CONTROLLER_MAPPING_ROOT + ENTITY + "/" + movieId)
                .with(requestPostProcessor))
                .andExpect(status().isNotFound())

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logFailure(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void updateMovieTest() throws Exception {
        Movie movie1 = TestDataBuilder.constructSingleMovieEntitity1().get(0);
        Movie movie2 = TestDataBuilder.constructSingleMovieEntitity2().get(0);

        long movieId = movie1.getId();
        assertNotEquals(movie1, movie2);
        movie2.setId(movieId);

        String jsonedMovie = asJsonString(movie2);

        when(movieService.findMovie(movieId)).thenReturn(movie1);
//        when(movieService.updateMovie(movie2)).thenReturn(movie2);

        MvcResult mvr = mockMvc.perform(put(CONTROLLER_MAPPING_ROOT + ENTITY + "/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonedMovie)
                .with(requestPostProcessor))
                .andExpect(status().isOk())

                .andExpect(jsonPath("id", is((int) TestConstants.ID_2)))
                .andExpect(jsonPath("name", is(TestConstants.NAME_2)))
                .andExpect(jsonPath("description", is(TestConstants.DESCRIPTION_2)))
                .andExpect(jsonPath("premierDate", is(TestConstants.PREMIER_DATE_2.toString())))
                .andExpect(jsonPath("durationInMinutes", is(TestConstants.DURATION_IN_MINUTES_2)))
                .andExpect(jsonPath("budgetInDollars", is(TestConstants.BUDGET_IN_DOLLARS_2)))
                .andExpect(jsonPath("rating", closeTo(TestConstants.RATING_1, TestConstants.RATING_1)))

                .andReturn();

        Movie returnedMovie = fromJsonString(mvr.getResponse().getContentAsString());

        verify(movieService, times(1)).findMovie(movieId);
        verify(movieService, times(1)).updateMovie(returnedMovie);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void updateUnexistentMovieTest() throws Exception {
        Movie movie = TestDataBuilder.constructSingleMovieEntitity1().get(0);
        long movieId = movie.getId();

        when(movieService.findMovie(movieId)).thenReturn(null);

        MvcResult mvr = mockMvc.perform(put(CONTROLLER_MAPPING_ROOT + ENTITY + "/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(movie))
                .with(requestPostProcessor))
                .andExpect(status().isNotFound())

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logFailure(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void deleteMovieTest() throws Exception {
        Movie movie = TestDataBuilder.constructSingleMovieEntitity1().get(0);
        long movieId = movie.getId();

        when(movieService.findMovie(movieId)).thenReturn(movie);

        MvcResult mvr = mockMvc.perform(delete(CONTROLLER_MAPPING_ROOT + ENTITY + "/" + movieId)
                .with(requestPostProcessor))
                .andExpect(status().isNoContent())

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(movieService, times(1)).deleteMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void deleteUnexistentMovieTest() throws Exception {
        long movieId = 1;

        when(movieService.findMovie(movieId)).thenReturn(null);

        MvcResult mvr = mockMvc.perform(delete(CONTROLLER_MAPPING_ROOT + ENTITY + "/" + movieId)
                .with(requestPostProcessor))
                .andExpect(status().isNotFound())

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logFailure(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void deleteAllMoviesTest() throws Exception {
        doNothing().when(movieService).deleteAllMovies();

        MvcResult mvr = mockMvc.perform(delete(CONTROLLER_MAPPING_ROOT + ENTITY + "/")
                .with(requestPostProcessor))
                .andExpect(status().isNoContent())

                .andReturn();
        verify(movieService, times(1)).deleteAllMovies();
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
    }

    @Test
    public void rateMovieTest() throws Exception {

        int rate = 5;

        Movie movie = TestDataBuilder.constructSingleMovieEntitity1().get(0);
        long movieId = movie.getId();

        when(movieService.findMovie(movieId)).thenReturn(movie);

        MvcResult mvr = mockMvc.perform(post(CONTROLLER_MAPPING_ROOT + ENTITY + RATING + "/" + movieId)
                .param("rate", String.valueOf(rate))
                .with(requestPostProcessor))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logSuccess(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(rateService, times(1)).rateMovie(TestDataBuilder.constructRateEntity(rate, movieId), movie);
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);
        verifyNoMoreInteractions(rateService);

    }

    @Test
    public void rateNonexistentMovieTest() throws Exception {
        int rate = 5;
        long movieId = 1;
        when(movieService.findMovie(movieId)).thenReturn(null);

        MvcResult mvr = mockMvc.perform(post(CONTROLLER_MAPPING_ROOT + ENTITY + RATING + "/" + movieId)
                .param("rate", String.valueOf(rate))
                .with(requestPostProcessor))

                .andExpect(status().isNotFound())

                .andReturn();

        verify(movieService, times(1)).findMovie(movieId);
        verify(requestsLogService, times(1)).logReceived(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verify(requestsLogService, times(1)).logFailure(mvr.getRequest(), UsingThisTo.getCurrentDate());
        verifyNoMoreInteractions(movieService);
        verifyNoMoreInteractions(requestsLogService);

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
