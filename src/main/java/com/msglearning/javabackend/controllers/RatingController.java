package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.services.RatingService;
import com.msglearning.javabackend.services.TokenService;
import com.msglearning.javabackend.services.UserService;
import com.msglearning.javabackend.to.RatingTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_RATING })
public class RatingController {

    public static final String USER_ID_PATH = "/userId/{userId}";
    public static final String MOVIE_ID_PATH = "/movieId/{movieId}";
    public static final String SAVE_PATH = "/save";
    public static final String DELETE_PATH = "/delete/{ratingId}";
    public static final String UPDATE_PATH = "/update";

    @Autowired
    RatingService ratingService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @GetMapping(MOVIE_ID_PATH)
    public Optional<RatingTO> getByMovieId(@PathVariable Long movieId) {
        return ratingService.findByMovieId(movieId);
    }

    @GetMapping(USER_ID_PATH)
    public Optional<RatingTO> getByUserId(@PathVariable Long userId) {
        return ratingService.findByUserId(userId);
    }

    @PostMapping(SAVE_PATH)
    public Boolean save(@RequestHeader("Authorization") String bearerToken,
                        @RequestBody RatingTO ratingTO) {

        String userName = tokenService.resolveToken(bearerToken);

        Long userId = userService.findByEmail(userName).get().getId();
        if (userId == null) return false;

        ratingTO.setUserId(userId);

        return ratingService.save(ratingTO);
    }

    @PutMapping(UPDATE_PATH)
    public Boolean update(@RequestHeader("Authorization") String bearerToken,
                          @RequestBody RatingTO ratingTO) {

        if (!tokenService.validateToken(bearerToken)) return false;

        var userOptional = userService.findByEmail(tokenService.getUserEmail(bearerToken));
        if (userOptional.isEmpty()) return false;

        ratingTO.setUserId(userOptional.get().getId());

        return ratingService.update(ratingTO) != null;
    }

    @DeleteMapping(DELETE_PATH)
    public Boolean delete(@PathVariable Long ratingId) {
        return ratingService.deleteById(ratingId);
    }
}
