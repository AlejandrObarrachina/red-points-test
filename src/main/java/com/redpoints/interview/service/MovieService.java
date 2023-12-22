package com.redpoints.interview.service;

import com.redpoints.interview.exceptionModels.WrongIdException;
import com.redpoints.interview.mappers.MovieMapper;
import com.redpoints.interview.model.Movie;
import com.redpoints.interview.repository.MovieRepository;
import com.redpoints.interview.service.data.MovieEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final MovieMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);


    public MovieService(MovieRepository repository, MovieMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MovieEntity> getAllMovies() throws Exception {
        try {
            return repository.findAll();
        } catch (Exception e) {
            logger.error("There was an error on GetAllMovies in Service: "+ e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public MovieEntity getMovieById(Long id) throws WrongIdException {

        Optional<MovieEntity> movieEntity = repository.findById(id);
        if (movieEntity.isPresent()) {
            return movieEntity.get();
        } else {
            logger.error("Wrong id error on getMovieById in Service with id: " + id);
            throw new WrongIdException(id);
        }
    }

    public Object addMovie(Movie movie) throws Exception {
        try {
           return repository.save(mapper.modelToEntity(movie));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public Object updateMovie(Long id, Movie movie) throws WrongIdException {

            if (!repository.existsById(id)) {
                throw new WrongIdException(id);
            } else {
                MovieEntity movieToUpdate = getMovieById(id);
                movieToUpdate.setTitle(movie.getTitle());
                movieToUpdate.setYear(movie.getYear());
                movieToUpdate.setDirector(movie.getDirector());
                return repository.save(movieToUpdate);
            }
    }

    public ResponseEntity<Object> deleteMovie(Long id) throws WrongIdException {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.error("Wrong id error on deleteMovie in Service with id: " + id);
            throw new WrongIdException(id);
        }

    }
}
