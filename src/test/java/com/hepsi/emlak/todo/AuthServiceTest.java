package com.hepsi.emlak.todo;

import com.hepsi.emlak.todo.exception.UserAlreadyExistsException;
import com.hepsi.emlak.todo.request.AuthRequest;
import com.hepsi.emlak.todo.request.UserRequest;
import com.hepsi.emlak.todo.response.LoginResponse;
import com.hepsi.emlak.todo.response.RegisterResponse;
import com.hepsi.emlak.todo.response.UserResponse;
import com.hepsi.emlak.todo.security.JwtTokenProvider;
import com.hepsi.emlak.todo.service.AuthService;
import com.hepsi.emlak.todo.service.UserService;
import com.hepsi.emlak.todo.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Authentication authentication;

    private AuthService authService;

    private AuthRequest loginRequest;

    private AuthRequest registerRequest;

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(authenticationManager, jwtTokenProvider, userService, passwordEncoder);
        loginRequest = new AuthRequest();
        loginRequest.setUserName("testUser");
        loginRequest.setPassword("testPassword");

        registerRequest = new AuthRequest();
        registerRequest.setUserName("testUser");
        registerRequest.setPassword("testPassword");

        userResponse = new UserResponse();
        userResponse.setUserId("17f1ff5f-4fd9-4916-824e-115c9ce54737");
    }

    @Test
    void testLoginSuccess() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtTokenProvider.generateJwtToken(any(Authentication.class)))
                .thenReturn("testJwtToken");

        when(userService.getUserByUserName(anyString()))
                .thenReturn(userResponse);

        LoginResponse loginResponse = authService.login(loginRequest);

        Assertions.assertEquals("Bearer testJwtToken", loginResponse.getToken());
        Assertions.assertEquals("17f1ff5f-4fd9-4916-824e-115c9ce54737", loginResponse.getUserId());

        Mockito.verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtTokenProvider, times(1)).generateJwtToken(any(Authentication.class));
        Mockito.verify(userService, times(1)).getUserByUserName(anyString());
    }

    @Test
    void testRegister_WhenUserAlreadyExists_ThrowsException() {


        when(userService.getUserByUserName(registerRequest.getUserName())).thenReturn(userResponse);

        UserAlreadyExistsException exception = Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            authService.register(registerRequest);
        });

        Assertions.assertEquals("User already exists: testUser", exception.getMessage());
        Mockito.verify(userService, times(1)).getUserByUserName(registerRequest.getUserName());
        Mockito.verify(userService, never()).saveUser(any(UserRequest.class));
    }

    @Test
    void testRegister_WhenUserDoesNotExist_SuccessfullyRegisters() {
        when(userService.getUserByUserName(registerRequest.getUserName())).thenReturn(null);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");

        RegisterResponse registerResponse = authService.register(registerRequest);

        Assertions.assertNotNull(registerResponse);
        Assertions.assertEquals("User successfully registered", registerResponse.getMessage());
        verify(userService, times(1)).getUserByUserName(registerRequest.getUserName());
        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
        verify(userService, times(1)).saveUser(any(UserRequest.class));
    }

}
