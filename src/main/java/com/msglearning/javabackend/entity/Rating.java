package com.msglearning.javabackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = Rating.TABLE_NAME)
@Entity
public class Rating {

    static final String TABLE_NAME = "ratings";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer value;

    @Column
    private String comment;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Rating() {
        this.id = null;
        this.value = null;
        this.comment = null;
        this.date = null;
        this.movie = null;
        this.user = null;
    }

    public Rating(Long id, Integer value, String comment, LocalDate date, Movie movie, User user) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.date = date;
        this.movie = movie;
        this.user = user;
    }
}
