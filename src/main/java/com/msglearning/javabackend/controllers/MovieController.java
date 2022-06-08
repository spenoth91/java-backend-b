package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.services.MovieService;
import com.msglearning.javabackend.services.TokenService;
import com.msglearning.javabackend.services.UserService;
import com.msglearning.javabackend.to.MovieTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_MOVIE })
public class MovieController {

    public static final String ALL_PATH = "/all";
    public static final String KEYWORD_PATH = "/k/{k}";
    public static final String SAVE_PATH = "/save";

    @Autowired
    MovieService movieService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @GetMapping(ALL_PATH)
    public List<Movie> getAll() {
        return this.movieService.findAll();
    }

    @GetMapping(KEYWORD_PATH)
    public List<Movie> getByKeyword(@PathVariable String k) {
        return movieService.findByTitle(k).get();
    }

    @PostMapping(SAVE_PATH)
    public Boolean saveMovie(@RequestHeader("Authorization") String bearerToken,
                             @RequestBody MovieTO movieTO) {

        System.out.println(movieTO);

        String userName = tokenService.resolveToken(bearerToken);

        if (userService.findByEmail(userName).isEmpty()) return false;
        System.out.println("all good");

        return movieService.save(movieTO) != null;
    }
}
