package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.services.RatingService;
import com.msglearning.javabackend.to.RatingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_RATING })
public class RatingController {

    public static final String USER_ID_PATH = "/userId/{userId}";
    public static final String MOVIE_ID_PATH = "/movieId/{movieId}";
    public static final String SAVE_PATH = "/save";
    public static final String DELETE_PATH = "/delete";

    @Autowired
    RatingService ratingService;

    @GetMapping(MOVIE_ID_PATH)
    public Optional<RatingTO> getByMovieId(@PathVariable Long movieId) {
        return ratingService.findByMovieId(movieId);
    }

    @GetMapping(USER_ID_PATH)
    public Optional<RatingTO> getByUserId(@PathVariable Long userId) {
        return ratingService.findByUserId(userId);
    }

    @PostMapping(SAVE_PATH)
    public RatingTO save(@RequestBody RatingTO ratingTO) {
        return ratingService.save(ratingTO);
    }

    @DeleteMapping(DELETE_PATH)
    public void delete(@RequestBody Long ratingId) {
        ratingService.deleteById(ratingId);
    }
}
