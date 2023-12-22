package com.redpoints.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpoints.interview.Application;
import com.redpoints.interview.initializer.DatabaseLoader;
import com.redpoints.interview.model.Movie;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebAppConfiguration
public class MovieControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private DatabaseLoader databaseLoader;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        databaseLoader.run();
    }
    @Test
    @Order(1)
    public void IsGetAllMovies_ReturningListOfMovies_True() throws Exception {
        this.mockMvc.perform(get("/api/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andReturn();
    }
    @Test
    public void IsGetMovieById_ReturningRightMovie_True() throws Exception {

        this.mockMvc.perform(get("/api/movies/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Tenet"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
    @Test
    public void IsAddMovie_CreatingANewMovie_True() throws Exception {
        Movie newMovie = new Movie();
        newMovie.setDirector("Nolan");
        newMovie.setTitle("Interstellar");
        newMovie.setYear(2014);

        this.mockMvc.perform(post("/api/movies")
                        .content(objectMapper
                                .writeValueAsString(newMovie))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Movie successfully added to DB"))
                .andReturn();
    }
    @Test
    public void IsUpdateMovie_UpdatesCorrectlyMovie_True() throws Exception {
        Movie newMovie = new Movie();
        newMovie.setDirector("Nolan");
        newMovie.setTitle("Interstellar");
        newMovie.setYear(2014);
        this.mockMvc.perform(put("/api/movies/3")
                .content(objectMapper
                .writeValueAsString(newMovie))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie successfully updated to DB"))
                .andReturn();

    }


    @Test
    public void IsDeleteMovie_DeletingCorrectly_True() throws Exception {
        this.mockMvc.perform(delete("/api/movies/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie successfully deleted from DB"))
                .andReturn();
    }

}
