package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.RatingConverter;
import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.MovieRepository;
import com.msglearning.javabackend.repositories.RatingRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.RatingTO;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Boolean save(RatingTO ratingTO) {
        Rating rating = RatingConverter.convertToEntity(ratingTO);
        rating.setUser(userRepository.findById(ratingTO.getUserId()).get());
        rating.setMovie(movieRepository.findById(ratingTO.getMovieId()).get());
        rating.setDate(LocalDate.now());
        if (!validateComment(rating.getComment())) {
            return false;
        }
        if (!validateDate(rating.getDate())) {
            return false;
        }
        if (!validateValue(rating.getValue())) {
            return false;
        }
        if (!validateMovie(rating.getMovie())) {
            return false;
        }
        if (!validateUser(rating.getUser())) {
            return false;
        }
        ratingRepository.save(rating);
        return true;
    }

    public Rating update(RatingTO ratingTO) {
        var ratingOptional = ratingRepository.findById(ratingTO.getId());
        if (ratingOptional.isEmpty()) return null;
        var rating = ratingOptional.get();
        rating.setComment(rating.getComment());
        rating.setValue(rating.getValue());
        rating.setDate(LocalDate.now());
        return ratingRepository.save(rating);
    }

    public void delete(RatingTO ratingTO) {
        ratingRepository.delete(RatingConverter.convertToEntity(ratingTO));
    }

    public Boolean deleteById(Long ratingId) {
        try {
            ratingRepository.deleteById(ratingId);
        }
        catch (EmptyResultDataAccessException ex) {
            return false;
        }
        return true;
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
