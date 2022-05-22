package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.entity.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long > {

    @Query("SELECT m FROM Movie m WHERE m.title like %:title%")
    Optional<Movie> getMovieByTitle(@Param("title") String title);

    @Query("SELECT m FROM Movie m WHERE m.duration BETWEEN :minDuration and :maxDuration")
    Optional<List<Movie>> getMoviesByDurationInterval(@Param("maxDuration") Integer minDuration, @Param("maxDuration") Integer maxDuration);

    @Query("SELECT m FROM Movie m WHERE m.year BETWEEN :minYear and :maxYear")
    Optional<List<Movie>> getMoviesByYearInterval(@Param("maxYear") Integer minYear, @Param("maxYear") Integer maxYear);

    @Query("SELECT m FROM Movie m WHERE m.category like %:category%")
    Optional<List<Movie>> getMoviesByCategory(@Param("category") String category);

}
