package com.hepsi.emlak.todo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
