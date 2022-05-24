package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.RatingConverter;
import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.MovieRepository;
import com.msglearning.javabackend.repositories.RatingRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.RatingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;

    public RatingTO save(RatingTO ratingTO) {
        Rating rating = RatingConverter.convertToEntity(ratingTO);
        rating.setUser(userRepository.findById(ratingTO.getUserId()).get());
        rating.setMovie(movieRepository.findById(ratingTO.getMovieId()).get());
        rating.setDate(LocalDate.now());
        if (!validateComment(rating.getComment())) {
            return null;
        }
        if (!validateDate(rating.getDate())) {
            return null;
        }
        if (!validateValue(rating.getValue())) {
            return null;
        }
        if (!validateMovie(rating.getMovie())) {
            return null;
        }
        if (!validateUser(rating.getUser())) {
            return null;
        }
        return RatingConverter.convertToTO(ratingRepository.save(rating));
    }

    public void delete(RatingTO ratingTO) {
        ratingRepository.delete(RatingConverter.convertToEntity(ratingTO));
    }

    public void deleteById(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public Optional<RatingTO> findByMovieId(Long id) {
        return ratingRepository.findByMovieId(id)
                .map(RatingConverter::convertToTO);
    }

    public Optional<RatingTO> findByUserId(Long id) {
        return ratingRepository.findByUserId(id)
                .map(RatingConverter::convertToTO);
    }

    private Boolean validateValue(Integer value) {
        return value > 0 && value <= 5;
    }

    private Boolean validateComment(String comment) {
        // in case we want to validate something about the comment
        return true;
    }

    private Boolean validateDate(LocalDate date) {
        return date != null;
    }

    private Boolean validateMovie(Movie movie) {
        return movie != null;
    }

    private Boolean validateUser(User user) {
        return user != null;
    }
}
