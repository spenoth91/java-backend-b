package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    /*
        For native query use:
        @Query(value = "SELECT r FROM Ratings r WHERE r.movieId = :id", nativeQuery = true)
     */
    @Query("SELECT r FROM Rating r WHERE r.movie = :id")
    List<Rating> getRatingsByMovieId(@Param("id") Long id);

    @Query("SELECT r FROM Rating r WHERE r.user = :id")
    List<Rating> getRatingsByUserId(@Param("id") Long id);
}
