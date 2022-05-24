package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    /*
        For native query:
        @Query(value = "SELECT * FROM Ratings WHERE movie_id = :id", nativeQuery = true)
    */
    @Query("SELECT r FROM Rating r WHERE r.movie.id = :id")
    Optional<Rating> findByMovieId(@Param("id") Long id);

    @Query("SELECT r FROM Rating r WHERE r.user.id = :id")
    Optional<Rating> findByUserId(@Param("id") Long id);
}
