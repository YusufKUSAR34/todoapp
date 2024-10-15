package com.hepsi.emlak.todo.mapper;


import com.hepsi.emlak.todo.entity.Todo;
import com.hepsi.emlak.todo.request.TodoRequest;
import com.hepsi.emlak.todo.response.TodoResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TodoMapper {

    @Mapping(target = "id", ignore = true)
    Todo todoRequestToTodo(TodoRequest todoRequest);

    TodoResponse todoToTodoResponse(Todo todo);

    TodoRequest todoToTodoRequest(Todo todo);
}
