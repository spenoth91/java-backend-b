package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.MovieConverter;
import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.repositories.MovieRepository;
import com.msglearning.javabackend.to.MovieTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public MovieTO save(MovieTO movieTO) {
        Movie movie = MovieConverter.convertToEntity(movieTO);

        if (!validateTitle(movie.getTitle())) {
            System.out.println(1);
            return null;
        }
        if (!validateDuration(movie.getDuration())) {
            System.out.println(2);
            return null;
        }
        if (!validateYear(movie.getYear())) {
            System.out.println(3);
            return null;
        }
        if(!validateDirector(movie.getDirector())) {
            System.out.println(4);
            return null;
        }
        if(!validateCategory(movieTO.getCategory())){
            System.out.println(5);
            return null;
        }

        System.out.println("all good in service");
        return MovieConverter.convertToTO(movieRepository.save(movie));
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<List<Movie>> findByTitle(String title) { return movieRepository.getMovieByTitle(title); }

    public Optional<List<Movie>> findByDuration(Integer min, Integer max) { return movieRepository.getMoviesByDurationInterval(min, max); }

    public Optional<List<Movie>> findByYear(Integer min, Integer max) { return movieRepository.getMoviesByYearInterval(min, max); }

    public Optional<List<Movie>> findByCategory(String category) { return movieRepository.getMoviesByCategory(category); }

    public List<Movie> findAll() { return (List<Movie>) movieRepository.findAll(); }

    private boolean validateTitle(String title) {
        return !title.isEmpty();
    }

    private boolean validateYear(Integer year) {
        return year > 0;
    }

    private boolean validateDuration(Integer duration) {
        return duration > 0;
    }

    private boolean validateDirector(String director) {
        return !(director == null || director.length() == 0);
    }

    private boolean validateCategory(String category) {
        return !(category == null || category.length() == 0);
    }


}
