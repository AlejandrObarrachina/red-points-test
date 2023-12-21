package com.redpoints.interview.controller;

import com.redpoints.interview.exceptionModels.WrongIdException;
import com.redpoints.interview.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MovieControllerIntegrationTest {
    @Autowired
    private MovieController movieController;

    @Test
    public void IsAddMovie_CreatingSingleMovie_True() throws Exception {

        Movie actualMovie = movieController.getMovieById(1L);

        assertEquals(1L, actualMovie.getId());
        assertNotNull(actualMovie);
        assertEquals("Tenet", actualMovie.getTitle());
        assertEquals("Christopher Nolan", actualMovie.getDirector());
        assertEquals(2020, actualMovie.getYear());


    }

    @Test
    public void IsGetAllMovies_ReturningListOfMovies_True() throws Exception {

        List<Movie> result = movieController.getAllMovies();

        assertNotNull(result);
        assertEquals(3, result.stream().count());
    }


    @Test
    public void IsUpdateMovie_UpdatesCorrectlyMovie_True() throws Exception {

        Movie movieToUpdate = new Movie();
        movieToUpdate.setTitle("Armageddon");
        movieToUpdate.setDirector("Michael Bay");
        movieToUpdate.setYear(1998);
        Movie actualMovieFromDB = movieController.getMovieById(1L);

        assertNotEquals(movieToUpdate.getTitle(), actualMovieFromDB.getTitle());

        movieController.updateMovie(1L, movieToUpdate);
        Movie updatedMovieFromDB = movieController.getMovieById(1L);

        assertEquals(movieToUpdate.getTitle(), updatedMovieFromDB.getTitle());
        assertEquals(movieToUpdate.getDirector(), updatedMovieFromDB.getDirector());
        assertEquals(movieToUpdate.getYear(), updatedMovieFromDB.getYear());
    }


    @Test
    public void IsDeleteMovie_DeletingCorrectly_True() throws Exception {

        Movie newMovie = new Movie();
        newMovie.setYear(1995);
        newMovie.setTitle("Pulp Fiction");
        newMovie.setDirector("Quentin Tarantino");
        movieController.addMovie(newMovie);

        List<Movie> movies = movieController.getAllMovies();
        assertNotNull(movies);
        assertEquals(4, movies.size());

        movieController.deleteMovie(3L);
        List<Movie> newMovies = movieController.getAllMovies();
        assertEquals(3, newMovies.size());

    }

}
