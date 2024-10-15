package com.hepsi.emlak.todo.service;

import com.hepsi.emlak.todo.request.AuthRequest;
import com.hepsi.emlak.todo.response.LoginResponse;
import com.hepsi.emlak.todo.response.RegisterResponse;

public interface AuthService {
    LoginResponse login(AuthRequest request);

    RegisterResponse register(AuthRequest request);
}
