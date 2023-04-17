package com.gymms.entity.dto;

import lombok.Data;

@Data
public class LoginFormDto {
    private String phone;
    private String code;
    private String password;
    private String nickName;
}