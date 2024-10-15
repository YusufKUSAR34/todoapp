package com.hepsi.emlak.todo.service.impl;

import com.hepsi.emlak.todo.entity.User;
import com.hepsi.emlak.todo.mapper.UserMapper;
import com.hepsi.emlak.todo.repository.UserRepository;
import com.hepsi.emlak.todo.request.UserRequest;
import com.hepsi.emlak.todo.response.UserResponse;
import com.hepsi.emlak.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse saveUser(UserRequest request) {
        User newUser = userMapper.userRequestToUser(request);
        userRepository.save(newUser);
        return userMapper.userToUserResponse(newUser);
    }
}
