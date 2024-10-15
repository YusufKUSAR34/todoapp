package com.hepsi.emlak.todo.mapper;


import com.hepsi.emlak.todo.entity.User;
import com.hepsi.emlak.todo.request.UserRequest;
import com.hepsi.emlak.todo.response.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User userRequestToUser(UserRequest userRequestDTO);

    @Mapping(target = "userId", source = "id")
    UserResponse userToUserResponse(User user);
}
