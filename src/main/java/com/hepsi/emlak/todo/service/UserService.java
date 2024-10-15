package com.hepsi.emlak.todo.service;

import com.hepsi.emlak.todo.request.UserRequest;
import com.hepsi.emlak.todo.response.UserResponse;

public interface UserService {
    UserResponse getUserByUserName(String userName);

    UserResponse saveUser(UserRequest request);
}
