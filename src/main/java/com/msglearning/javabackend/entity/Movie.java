package com.msglearning.javabackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = Movie.TABLE_NAME)
@Entity
public class Movie {

    static final String TABLE_NAME = "movies";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column
    private Integer duration;

    @Column
    private Integer year;

    @Column
    private String director;

    @Column
    private String category;

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings = new ArrayList<>();

    public Movie() {
        this.id = null;
        this.title = null;
        this.duration = null;
        this.year = null;
        this.director = null;
        this.category = null;
    }

    public Movie(Long id, String title, int duration, int year, String director, String category) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.year = year;
        this.director = director;
        this.category = category;
    }
}
