package com.msglearning.javabackend.to;

import com.msglearning.javabackend.entity.Movie;
import com.msglearning.javabackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RatingTO implements Serializable {

    private Long id;

    private Integer value;

    private String comment;

    private LocalDate date;

    private Movie movie;

    private User user;
}
