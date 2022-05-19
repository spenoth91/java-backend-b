package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.to.RatingTO;

public class RatingConverter {

    public static final RatingTO convertToTO(Rating entity) {
        return new RatingTO(
                entity.getId(),
                entity.getValue(),
                entity.getComment(),
                entity.getDate(),
                entity.getMovie(),
                entity.getUser()
        );
    }

    public static final Rating convertToEntity(RatingTO ratingTO) {
        return new Rating(
                ratingTO.getId(),
                ratingTO.getValue(),
                ratingTO.getComment(),
                ratingTO.getDate(),
                ratingTO.getMovie(),
                ratingTO.getUser()
        );
    }
}
