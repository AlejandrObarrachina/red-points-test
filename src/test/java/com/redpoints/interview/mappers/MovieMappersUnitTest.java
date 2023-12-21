package com.redpoints.interview.mappers;

import com.redpoints.interview.model.Movie;
import com.redpoints.interview.service.data.MovieEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MovieMappersUnitTest {
    @InjectMocks
    private MovieMapper mapper;
    @Test
    public void IsModelToEntity_MappingCorrectly_True(){
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setYear(2022);
        movie.setDirector("Director");

        MovieEntity movieEntity = mapper.modelToEntity(movie);

        assertEquals(movie.getId(), movieEntity.getId());
        assertEquals(movie.getTitle(), movieEntity.getTitle());
        assertEquals(movie.getYear(), movieEntity.getYear());
        assertEquals(movie.getDirector(), movieEntity.getDirector());
    }

    @Test
    public void IsEntityToModel_MappingCorrectly_True(){
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(1L);
        movieEntity.setTitle("Test Movie");
        movieEntity.setYear(2022);
        movieEntity.setDirector("Director");

        Movie movie = mapper.entityToModel(movieEntity);

        assertEquals(movie.getId(), movieEntity.getId());
        assertEquals(movie.getTitle(), movieEntity.getTitle());
        assertEquals(movie.getYear(), movieEntity.getYear());
        assertEquals(movie.getDirector(), movieEntity.getDirector());
    }

    @Test
    public void IsEntitiesToModels_MappingCorrectly_True() {
        MovieEntity movieEntity1 = new MovieEntity();
        movieEntity1.setId(1L);
        movieEntity1.setTitle("Test Movie 1");
        movieEntity1.setYear(2022);
        movieEntity1.setDirector("Director 1");

        MovieEntity movieEntity2 = new MovieEntity();
        movieEntity2.setId(2L);
        movieEntity2.setTitle("Test Movie 2");
        movieEntity2.setYear(2023);
        movieEntity2.setDirector("Director 2");

        List<MovieEntity> movieEntities = List.of(movieEntity1, movieEntity2);

        // Act
        List<Movie> movies = mapper.entitiesToModels(movieEntities);

        // Assert
        assertNotNull(movies);
        assertEquals(2, movies.size());

        Movie movie1 = movies.get(0);
        assertEquals(movieEntity1.getId(), movie1.getId());
        assertEquals(movieEntity1.getTitle(), movie1.getTitle());
        assertEquals(movieEntity1.getYear(), movie1.getYear());
        assertEquals(movieEntity1.getDirector(), movie1.getDirector());

        Movie movie2 = movies.get(1);
        assertEquals(movieEntity2.getId(), movie2.getId());
        assertEquals(movieEntity2.getTitle(), movie2.getTitle());
        assertEquals(movieEntity2.getYear(), movie2.getYear());
        assertEquals(movieEntity2.getDirector(), movie2.getDirector());
    }
}
