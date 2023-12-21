package com.redpoints.interview.controller;

import com.redpoints.interview.config.ResponseHandler;
import com.redpoints.interview.exceptionModels.WrongIdException;
import com.redpoints.interview.mappers.MovieMapper;
import com.redpoints.interview.model.Movie;
import com.redpoints.interview.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @GetMapping
    public List<Movie> getAllMovies() throws Exception {
        return movieMapper.entitiesToModels(movieService.getAllMovies());

    }

    @GetMapping(value = "/movie/{id}", produces = "application/json")
    public Movie getMovieById(@PathVariable Long id) throws WrongIdException {
        return movieMapper.entityToModel(movieService.getMovieById(id));
    }

    @PostMapping(value = "/movie", consumes = "application/json")
    public ResponseEntity<Object> addMovie(@RequestBody @Valid Movie movie) throws Exception {
        return ResponseHandler.responseBuilder("Movie successfully added to DB", HttpStatus.OK, movieService.addMovie(movie));

    }

    @PutMapping(value = "/movie/{id}", consumes = "application/json")
    public ResponseEntity<Object> updateMovie(@PathVariable Long id, @RequestBody @Valid Movie movie) throws WrongIdException {
        return ResponseHandler.responseBuilder("Movie successfully updated to DB", HttpStatus.OK, movieService.updateMovie(id, movie));
    }

    @DeleteMapping(value = "/movie/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable Long id) throws WrongIdException {
        return ResponseHandler.responseBuilder("Movie successfully deleted from DB", HttpStatus.OK, movieService.deleteMovie(id));
    }
}
