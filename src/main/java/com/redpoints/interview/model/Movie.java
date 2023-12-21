package com.redpoints.interview.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class Movie {
    private Long id;
    @NotBlank(message = "Title can not be null")
    private String title;
    @NotBlank(message = "Director can not be null")
    private String director;
    @Range(min = 1878, message = "Movie can not be created before 1878, search: The Horse in Motion")
    private int year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
