package com.hepsi.emlak.todo.service;

import com.hepsi.emlak.todo.request.TodoRequest;
import com.hepsi.emlak.todo.response.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse addTodo(TodoRequest todoRequestDTO);

    TodoResponse editTodo(String todoId, TodoRequest todoRequestDTO);

    void removeTodo(String userId, String todoId);

    TodoResponse getUserTodo(String userId, String todoId);

    TodoResponse todoComplete(String todoId);

    List<TodoResponse> getUserTodoList(String userId);
}
