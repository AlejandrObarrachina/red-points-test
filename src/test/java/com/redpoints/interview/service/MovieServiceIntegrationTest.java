package com.redpoints.interview.service;

import com.redpoints.interview.exceptionModels.WrongIdException;
import com.redpoints.interview.model.Movie;
import com.redpoints.interview.service.data.MovieEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;




    @Test
    public void IsAddMovie_CreatingSingleMovie_True() throws Exception {

        List<MovieEntity> result = movieService.getAllMovies();
        assertNotNull(result);
        assertEquals(3, result.stream().count());

        Movie newMovie =  new Movie();
        newMovie.setDirector("DirectorTest");
        newMovie.setTitle("TitleTest");
        movieService.addMovie(newMovie);

        List<MovieEntity> newResult = movieService.getAllMovies();
        assertNotNull(result);
        assertEquals(4, newResult.stream().count());

    }

    @Test
    public void IsAddMovie_CreatingSingleMovie_False() throws Exception {

        List<MovieEntity> result = movieService.getAllMovies();
        assertNotNull(result);
        assertEquals(3, result.stream().count());

        Movie newMovie =  new Movie();
        newMovie.setDirector("DirectorTest");
        newMovie.setTitle("TitleTest");
        movieService.addMovie(newMovie);

        List<MovieEntity> newResult = movieService.getAllMovies();
        assertNotNull(result);
        assertEquals(4, newResult.stream().count());

    }


    @Test
    public void IsGetAllMovies_ReturningListOfMovies_True() throws Exception {

        List<MovieEntity> result = movieService.getAllMovies();
        assertNotNull(result);
        assertEquals(3, result.stream().count());

    }

    @Test
    public void IsGetAllMovies_ReturningListOfMovies_False() throws Exception {

        List<MovieEntity> result = movieService.getAllMovies();
        assertNotEquals(0, result.stream().count());

    }

    @Test
    public void IsUpdateMovie_UpdatesCorrectlyMovie_True() throws Exception {

        Movie movieToUpdate = new Movie();
        movieToUpdate.setTitle("Armageddon");
        movieToUpdate.setDirector("Michael Bay");
        movieToUpdate.setYear(1998);

        movieService.updateMovie(1L, movieToUpdate);
        MovieEntity updatedMovieFromDB = movieService.getMovieById(1L);

        assertEquals("Armageddon", updatedMovieFromDB.getTitle());
        assertEquals("Michael Bay", updatedMovieFromDB.getDirector());
        assertEquals(1998, updatedMovieFromDB.getYear());
    }

    @Test
    public void IsDeleteMovie_DeletingCorrectly_True() throws Exception {

        Movie newMovie = new Movie();
        newMovie.setYear(1995);
        newMovie.setTitle("Pulp Fiction");
        newMovie.setDirector("Quentin Tarantino");
        movieService.addMovie(newMovie);

        List<MovieEntity> movies = movieService.getAllMovies();
        assertNotNull(movies);
        assertEquals(4, movies.size());

        movieService.deleteMovie(3L);
        List<MovieEntity> newMovies = movieService.getAllMovies();
        assertEquals(3, newMovies.size());

    }

    @Test
    public void IsDeleteMovie_DeletingCorrectly_False() throws Exception {

        Long nonexistentId = 999L;

        WrongIdException exception = assertThrows(WrongIdException.class, () -> {
            movieService.deleteMovie(nonexistentId);
        });

        String expectedMessage = "Movie not found with ID: " + nonexistentId;
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

    }

}
