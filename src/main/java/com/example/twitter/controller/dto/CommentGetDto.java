package com.example.twitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {
    private Long id;

    private Long userId;

    @NotBlank
    private String text;

    @NotNull
    private Date publishedAt;

    @NotNull
    private Date updatedAt;
}
