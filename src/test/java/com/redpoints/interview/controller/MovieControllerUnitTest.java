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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

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

        List<Movie> result = movieController.getAllMovies();

        assertNotNull(result);
        assertEquals(2, result.size());

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

        Movie result = movieController.getMovieById(movieId);

        assertNotNull(result);
        assertEquals(movieId, result.getId());

        verify(movieService, times(1)).getMovieById(movieId);
        verify(movieMapper, times(1)).entityToModel(mockMovie);
    }

    @Test
    void IsAddMovie_CallingAddMovieMethodOfService_True() {
        Movie movie = new Movie();
        movieController.addMovie(movie);

        verify(movieService, times(1)).addMovie(movie);
    }

    @Test
    void IsUpdateMovie_CallingUpdateMovieMethodOfService_True() throws WrongIdException {
        Long movieId = 1L;
        Movie movie = new Movie();

        movieController.updateMovie(movieId, movie);

        verify(movieService, times(1)).updateMovie(movieId, movie);
    }

    @Test
    void IsDeleteMovie_CallingUpdateMovieMethodOfService_True() throws WrongIdException {
        Long movieId = 1L;

        movieController.deleteMovie(movieId);

        verify(movieService, times(1)).deleteMovie(movieId);
    }

}