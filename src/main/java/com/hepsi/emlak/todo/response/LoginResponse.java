package com.hepsi.emlak.todo.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String userId;
}
