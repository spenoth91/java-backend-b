package com.msglearning.javabackend.to;

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

    private Long movieId;

    private Long userId;
}
