package com.redpoints.interview.mappers;

import com.redpoints.interview.model.Movie;
import com.redpoints.interview.service.data.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class MovieMapper {

	public MovieEntity modelToEntity(Movie movie) {
		MovieEntity entity =  new MovieEntity();
		entity.setId(movie.getId());
		entity.setYear(movie.getYear());
		entity.setDirector(movie.getDirector());
		entity.setTitle(movie.getTitle());
		return entity;
	}

	public Movie entityToModel(MovieEntity movieEntity) {
		Movie movie = new Movie();
		movie.setId(movieEntity.getId());
		movie.setTitle(movieEntity.getTitle());
		movie.setDirector(movieEntity.getDirector());
		movie.setYear(movieEntity.getYear());

		return movie;
	}

	public List<Movie> entitiesToModels(List<MovieEntity> movieEntities) {
		return movieEntities.stream().map(this::entityToModel).toList();
	}
}
