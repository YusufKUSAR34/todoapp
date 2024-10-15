package com.hepsi.emlak.todo;

import com.hepsi.emlak.todo.entity.User;
import com.hepsi.emlak.todo.mapper.UserMapper;
import com.hepsi.emlak.todo.repository.UserRepository;
import com.hepsi.emlak.todo.request.UserRequest;
import com.hepsi.emlak.todo.response.UserResponse;
import com.hepsi.emlak.todo.service.UserService;
import com.hepsi.emlak.todo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper);

    }

    @Test
    void testGetUserByUserName_Success() {
        String userName = "testUser";
        User mockUser = new User();
        mockUser.setUserName(userName);

        UserResponse mockResponse = new UserResponse();
        mockResponse.setUserName(userName);

        when(userRepository.findByUserName(userName)).thenReturn(mockUser);
        when(userMapper.userToUserResponse(mockUser)).thenReturn(mockResponse);

        UserResponse response = userService.getUserByUserName(userName);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(userName, response.getUserName());
        verify(userRepository, times(1)).findByUserName(userName);
        verify(userMapper, times(1)).userToUserResponse(mockUser);
    }

    @Test
    void testSaveUser_Success() {

        UserRequest request = new UserRequest();
        request.setUserName("newUser");

        User newUser = new User();
        newUser.setUserName(request.getUserName());

        UserResponse mockResponse = new UserResponse();
        mockResponse.setUserName(request.getUserName());

        when(userMapper.userRequestToUser(request)).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(userMapper.userToUserResponse(newUser)).thenReturn(mockResponse);

        UserResponse response = userService.saveUser(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("newUser", response.getUserName());
        verify(userMapper, times(1)).userRequestToUser(request);
        verify(userRepository, times(1)).save(newUser);
        verify(userMapper, times(1)).userToUserResponse(newUser);
    }

}
