package com.example.twitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetGetDto {
    @NotNull
    private Long id;

    @NotNull
    private String userId;

    @NotBlank
    private String text;

    @NotNull
    private Date publishedAt;

    @NotNull
    private Date updatedAt;

    private List<CommentGetDto> comments;
}
