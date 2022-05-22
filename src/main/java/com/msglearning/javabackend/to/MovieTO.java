package com.msglearning.javabackend.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class MovieTO implements Serializable {

    private Long id;

    private String title;

    private Integer duration;

    private Integer year;

    private String director;

    private String category;

}
