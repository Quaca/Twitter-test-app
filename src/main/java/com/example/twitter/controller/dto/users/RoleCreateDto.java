package com.example.twitter.controller.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleCreateDto {
    private String username;
    private String roleName;
}
