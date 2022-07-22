package com.example.twitter.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TweetDto {

    private Long id;

    @NotBlank
    private String text;

    @NotNull
    private Date publishedAt;

    @NotNull
    private Date updatedAt;
}
