package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.RatingConverter;
import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.RatingRepository;
import com.msglearning.javabackend.to.RatingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public RatingTO save(RatingTO ratingTO) {
        Rating rating = RatingConverter.convertToEntity(ratingTO);
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
