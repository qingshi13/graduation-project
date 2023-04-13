package com.gymms.entity.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer userId;
    private String nickName;
    private String picture;
    private String role;
}
