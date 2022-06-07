package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.MovieConverter;
import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.repositories.MovieRepository;
import com.msglearning.javabackend.to.MovieTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public MovieTO save(MovieTO movieTO) {
        Movie movie = MovieConverter.convertToEntity(movieTO);

        if (!validateTitle(movie.getTitle())) {
            return null;
        }
        if (!validateDuration(movie.getDuration())) {
            return null;
        }
        if (!validateYear(movie.getYear())) {
            return null;
        }
        if(!validateDirector(movie.getDirector())) {
            return null;
        }
        if(!validateCategory(movieTO.getCategory())){
            return null;
        }

        return MovieConverter.convertToTO(movieRepository.save(movie));
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> findByTitle(String title) { return movieRepository.getMovieByTitle(title); }

    public Optional<List<Movie>> findByDuration(Integer min, Integer max) { return movieRepository.getMoviesByDurationInterval(min, max); }

    public Optional<List<Movie>> findByYear(Integer min, Integer max) { return movieRepository.getMoviesByYearInterval(min, max); }

    public Optional<List<Movie>> findByCategory(String category) { return movieRepository.getMoviesByCategory(category); }

    public List<Movie> findAll() { return (List<Movie>) movieRepository.findAll(); }

    private boolean validateTitle(String title) {
        return  (title.length() > 0 && movieRepository.getMovieByTitle(title).isEmpty());
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

    public List<Movie> findFeatured() {
        List<Movie> movies = StreamSupport.stream(movieRepository.findAll().spliterator(), false).sorted((o1, o2) -> {
            //double avgRatingOfFirst = o1.getRatings().stream().mapToDouble(Rating::getValue).average().orElse(0);
            //double avgRatingOfSecond = o2.getRatings().stream().mapToDouble(Rating::getValue).average().orElse(0);
            return (o2.getYear() - o1.getYear());
        }).collect(Collectors.toList());

        return movies.size() > 5 ? movies.subList(0, 5) : movies;

    }
}
