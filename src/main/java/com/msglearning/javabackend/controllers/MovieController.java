package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_MOVIE })
public class MovieController {

    public static final String ALL_PATH = "/all";
    public static final String KEYWORD_PATH = "/k/{k}";

    @Autowired
    MovieService movieService;

    @GetMapping(ALL_PATH)
    public List<Movie> getAll() {
        return this.movieService.findAll();
    }

    @GetMapping(KEYWORD_PATH)
    public HashMap<String, Movie> getByKeyword(@PathVariable String k) {
        HashMap<String, Movie> movies = new HashMap<>();

        this.movieService.findByTitle(k)
                .ifPresent(movie -> movies.put(movie.getTitle(), movie));

        return movies;
    }
}
