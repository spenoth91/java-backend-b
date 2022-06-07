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
import java.util.stream.Collectors;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_MOVIE })
public class MovieController {

    public static final String ALL_PATH = "/all";
    public static final String KEYWORD_PATH = "/k/{k}";

    public static final String FEATURED_PATH = "/featured";

    @Autowired
    MovieService movieService;

    @GetMapping(ALL_PATH)
    public List<Movie> getAll() {
        return this.movieService.findAll();
    }

    @GetMapping(KEYWORD_PATH)
    public List<Movie> getByKeyword(@PathVariable String k) {
        return movieService.findByTitle(k).stream().collect(Collectors.toList());
    }

    @GetMapping(FEATURED_PATH)
    public List<Movie> getFeatured() {
        return movieService.findFeatured();
    }
}
