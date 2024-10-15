package com.hepsi.emlak.todo.service.impl;

import com.hepsi.emlak.todo.exception.UserAlreadyExistsException;
import com.hepsi.emlak.todo.request.AuthRequest;
import com.hepsi.emlak.todo.request.UserRequest;
import com.hepsi.emlak.todo.response.LoginResponse;
import com.hepsi.emlak.todo.response.RegisterResponse;
import com.hepsi.emlak.todo.response.UserResponse;
import com.hepsi.emlak.todo.security.JwtTokenProvider;
import com.hepsi.emlak.todo.service.AuthService;
import com.hepsi.emlak.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(AuthRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        UserResponse user = userService.getUserByUserName(loginRequest.getUserName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("Bearer " + jwtToken);
        loginResponse.setUserId(user.getUserId());
        return loginResponse;
    }

    @Override
    public RegisterResponse register(AuthRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (userService.getUserByUserName(registerRequest.getUserName()) != null) {
            throw new UserAlreadyExistsException("User already exists: " + registerRequest.getUserName());
        }
        UserRequest request = new UserRequest();
        request.setUserName(registerRequest.getUserName());
        request.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveUser(request);
        registerResponse.setMessage("User successfully registered");
        return registerResponse;
    }
}
