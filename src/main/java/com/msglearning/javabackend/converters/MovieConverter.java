package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.to.MovieTO;

public class MovieConverter {

    public static final MovieTO convertToTO(Movie entity) {
        return new MovieTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDuration(),
                entity.getYear(),
                entity.getDirector(),
                entity.getCategory()
        );
    }

    public static final Movie convertToEntity(MovieTO movieTO) {
        return new Movie(
                movieTO.getId(),
                movieTO.getTitle(),
                movieTO.getDuration(),
                movieTO.getYear(),
                movieTO.getDirector(),
                movieTO.getCategory()
        );
    }

}
