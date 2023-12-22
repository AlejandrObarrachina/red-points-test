package com.redpoints.interview.controller;

import com.redpoints.interview.exceptionModels.WrongIdException;
import com.redpoints.interview.mappers.MovieMapper;
import com.redpoints.interview.model.Movie;
import com.redpoints.interview.service.MovieService;
import com.redpoints.interview.service.data.MovieEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MovieControllerUnitTest {

    @Mock
    private MovieService movieService;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.movieController = new MovieController(this.movieService, this.movieMapper);
    }

    @Test
    void IsGetAllMovies_ReturningAMovieList_True() throws Exception {

        when(movieService.getAllMovies()).thenReturn(List.of(new MovieEntity(), new MovieEntity()));
        when(movieMapper.entitiesToModels(anyList())).thenReturn(List.of(new Movie(), new Movie()));

        ResponseEntity<Object> result = movieController.getAllMovies();
        HashMap<String, Object> body = (HashMap) result.getBody();
        List<Movie> data = (List<Movie>) body.get("data");
        assertNotNull(result);
        assertEquals(2, data.size());

        verify(movieService, times(1)).getAllMovies();
        verify(movieMapper, times(1)).entitiesToModels(anyList());
    }

    @Test
    void IsGetMovieById_ReturningAMovieWithMatchingId_True() throws WrongIdException {
        Long movieId = 1L;
        MovieEntity mockMovie = new MovieEntity();
        mockMovie.setId(movieId);
        Movie mappedMovie =  new Movie();
        mappedMovie.setId(movieId);
        when(movieService.getMovieById(movieId)).thenReturn(mockMovie);
        when(movieMapper.entityToModel(mockMovie)).thenReturn(mappedMovie);

        ResponseEntity<Object> result = movieController.getMovieById(movieId);
        HashMap<String, Object> body = (HashMap) result.getBody();
        Movie data = (Movie) body.get("data");

        assertNotNull(result);
        assertEquals(movieId, data.getId());

        verify(movieService, times(1)).getMovieById(movieId);
        verify(movieMapper, times(1)).entityToModel(mockMovie);
    }

    @Test
    void IsAddMovie_CallingAddMovieMethodOfService_True() throws Exception {
        Movie movie = new Movie();
        ResponseEntity<Object> result = movieController.addMovie(movie);
        HashMap<String, Object> body = (HashMap) result.getBody();
        assertEquals(HttpStatus.CREATED, body.get("httpStatus"));

        verify(movieService, times(1)).addMovie(movie);
    }

    @Test
    void IsUpdateMovie_CallingUpdateMovieMethodOfService_True() throws WrongIdException {
        Long movieId = 1L;
        Movie movie = new Movie();

        ResponseEntity<Object> result = movieController.updateMovie(movieId, movie);
        HashMap<String, Object> body = (HashMap) result.getBody();
        assertEquals(HttpStatus.OK, body.get("httpStatus"));
        verify(movieService, times(1)).updateMovie(movieId, movie);
    }

    @Test
    void IsDeleteMovie_CallingUpdateMovieMethodOfService_True() throws WrongIdException {
        Long movieId = 1L;

        ResponseEntity<Object> result = movieController.deleteMovie(movieId);
        HashMap<String, Object> body = (HashMap) result.getBody();
        assertEquals(HttpStatus.OK, body.get("httpStatus"));

        verify(movieService, times(1)).deleteMovie(movieId);
    }

}