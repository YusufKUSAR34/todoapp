package com.hepsi.emlak.todo.service.impl;

import com.hepsi.emlak.todo.entity.Todo;
import com.hepsi.emlak.todo.exception.TodoNotFoundException;
import com.hepsi.emlak.todo.mapper.TodoMapper;
import com.hepsi.emlak.todo.repository.TodoRepository;
import com.hepsi.emlak.todo.request.TodoRequest;
import com.hepsi.emlak.todo.response.TodoResponse;
import com.hepsi.emlak.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;


    @Override
    public TodoResponse addTodo(TodoRequest todoRequest) {
        var todo = todoMapper.todoRequestToTodo(todoRequest);
        todo.setUserId(todoRequest.getUserId());
        todo.setCompleted(todoRequest.getCompleted());
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setDate(todoRequest.getDate());
        todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public TodoResponse editTodo(String todoId, TodoRequest todoRequest) {
        var exitTodo = findTodo(todoId, todoRequest.getUserId());

        var newTodoContent = todoMapper.todoRequestToTodo(todoRequest);
        newTodoContent.setId(exitTodo.getId());
        newTodoContent = todoRepository.save(newTodoContent);

        return todoMapper.todoToTodoResponse(newTodoContent);
    }

    @Override
    public void removeTodo(String userId, String todoId) {
        var todo = findTodo(todoId, userId);
        todoRepository.delete(todo);
    }

    @Override
    public TodoResponse getUserTodo(String userId, String todoId) {
        var todo = findTodo(todoId, userId);
        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public TodoResponse todoComplete(String todoId) {
        var todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
        todo.setCompleted(true);
        todo = todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public List<TodoResponse> getUserTodoList(String userId) {
        var todoList = todoRepository.findAllByUserId(userId);

        return todoList.stream()
                .map(todoMapper::todoToTodoResponse)
                .collect(Collectors.toList());
    }

    private Todo findTodo(String todoId, String userId) {
        return todoRepository.findByIdAndUserId(todoId, userId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
    }
}
