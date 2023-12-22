package com.redpoints.interview.service;


import com.redpoints.interview.exceptionModels.WrongIdException;
import com.redpoints.interview.mappers.MovieMapper;
import com.redpoints.interview.model.Movie;
import com.redpoints.interview.repository.MovieRepository;
import com.redpoints.interview.service.data.MovieEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceUnitTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper mapper;
    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp()
    {
        this.movieService
                = new MovieService(this.movieRepository, this.mapper);
    }

    @Test
    public void IsGetAllMovies_CallingRightMethod_True() throws Exception {
        movieService.getAllMovies();
        verify(movieRepository, atLeast(1)).findAll();
    }
    @Test
    public void IsGetAllMovies_ReturningListOfMoviesWithCorrectValues_True() throws Exception {
        MovieEntity movie1 = new MovieEntity();
        movie1.setId(1L);
        movie1.setTitle("Interstellar");
        movie1.setDirector("Nolan");

        MovieEntity movie2 = new MovieEntity();
        movie2.setId(2L);
        movie2.setTitle("Django unchained");

        when(movieRepository.findAll()).thenReturn(List.of(movie1, movie2));

        List<MovieEntity> result = movieService.getAllMovies();

        assertAll("Check movie list",
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(2, result.size()),
                () -> assertSame(movie1, result.get(0)),
                () -> assertSame(movie2, result.get(1)),
                () -> assertSame("Interstellar", movie1.getTitle()),
                () -> assertSame("Nolan", movie1.getDirector())
        );
    }

    @Test
    public void IsGetAllMovies_ReturningListOfMoviesWithCorrectValues_False() throws Exception {

        when(movieRepository.findAll()).thenReturn(List.of());

        List<MovieEntity> result = movieService.getAllMovies();

        assertAll("Check movie list",
                () -> assertNotNull(result),
                () -> assertTrue(result.isEmpty()),
                () -> assertNotEquals(2, result.size())
        );
    }
    @Test
    public void IsUpdateMovie_ThrowingAnExceptionIfIdIsWrong_True() throws WrongIdException {
        Long nonExistingMovieId = 99L;
        Movie updatedMovie = new Movie();
        updatedMovie.setYear(2020);
        updatedMovie.setTitle("Interstellar");
        updatedMovie.setDirector("Nolan");

        when(movieRepository.existsById(nonExistingMovieId)).thenReturn(false);

        assertThrows(WrongIdException.class, () -> movieService.updateMovie(nonExistingMovieId, updatedMovie));

        verify(movieRepository, times(1)).existsById(nonExistingMovieId);
        verify(movieRepository, never()).save(any(MovieEntity.class));
    }
    @Test
    public void IsGetMovieById_ReturningMovieWithMatchingId_True() throws WrongIdException {
        Long movieId = 1L;
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieId);
        movieEntity.setTitle("Test Movie Title");

        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieEntity));

        MovieEntity result = movieService.getMovieById(movieId);

        assertEquals(movieEntity, result);
    }
    @Test
    public void IsGetMovieById_ReturningMovieWithMatchingId_False() {
        Long movieId = 6L;
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(WrongIdException.class, () -> movieService.getMovieById(movieId));
    }
    @Test
    public void IsAddMovie_SavingCorrectly_True() throws Exception {

        Movie movieToAdd = new Movie();
        movieToAdd.setTitle("Test Movie");
        movieToAdd.setYear(2022);
        movieToAdd.setDirector("Director");

        MovieEntity mappedEntity = new MovieEntity();
        mappedEntity.setTitle("Test Movie");
        mappedEntity.setYear(2022);
        mappedEntity.setDirector("Director");

        when(mapper.modelToEntity(movieToAdd)).thenReturn(mappedEntity);

        movieService.addMovie(movieToAdd);

        verify(movieRepository).save(mappedEntity);

    }
}