package com.example.twitter.controller.dto.users;

import com.example.twitter.controller.dto.CommentGetDto;
import com.example.twitter.controller.dto.TweetGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String country;

    private List<TweetGetDto> tweets;

    private List<CommentGetDto> comments;

    private List<Long> followers;

    private List<Long> following;

}
