package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.movieId = :id")
    List<Rating> getRatingsByMovieId(@Param("id") Long id);

    @Query("SELECT r FROM Rating r WHERE r.userId = :token")
    List<Rating> getRatingsByUserId(@Param("id") Long id);
}
