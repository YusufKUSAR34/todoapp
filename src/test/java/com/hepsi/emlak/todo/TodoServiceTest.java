package com.hepsi.emlak.todo;

import com.hepsi.emlak.todo.entity.Todo;
import com.hepsi.emlak.todo.mapper.TodoMapper;
import com.hepsi.emlak.todo.repository.TodoRepository;
import com.hepsi.emlak.todo.request.TodoRequest;
import com.hepsi.emlak.todo.response.TodoResponse;
import com.hepsi.emlak.todo.service.TodoService;
import com.hepsi.emlak.todo.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    String todoId;
    String userId;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private TodoMapper todoMapper;
    private TodoService todoService;
    private TodoRequest todoRequest;
    private Todo todo;

    @BeforeEach
    void setUp() {
        todoService = new TodoServiceImpl(todoRepository, todoMapper);
        todoRequest = new TodoRequest();
        todoRequest.setUserId("user1");
        todoRequest.setTitle("New Task");

        todo = new Todo();
        todo.setId("todo1");
        todo.setTitle("New Task");

        todoId = "todo1";
        userId = "user1";
    }

    @Test
    void testAddTodo_Success() {

        TodoResponse expectedResponse = new TodoResponse();
        expectedResponse.setId("todo1");
        expectedResponse.setTitle("New Task");

        when(todoMapper.todoRequestToTodo(todoRequest)).thenReturn(todo);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        when(todoMapper.todoToTodoResponse(todo)).thenReturn(expectedResponse);

        TodoResponse actualResponse = todoService.addTodo(todoRequest);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals("todo1", actualResponse.getId());
        verify(todoMapper, times(1)).todoRequestToTodo(todoRequest);
        verify(todoRepository, times(1)).save(todo);
        verify(todoMapper, times(1)).todoToTodoResponse(todo);
    }

    @Test
    void testEditTodo_Success() {

        todoRequest.setTitle("Updated Task");

        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setUserId(userId);

        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setTitle("Updated Task");

        TodoResponse expectedResponse = new TodoResponse();
        expectedResponse.setId(todoId);
        expectedResponse.setTitle("Updated Task");

        when(todoRepository.findByIdAndUserId(todoId, userId)).thenReturn(Optional.of(existingTodo));
        when(todoMapper.todoRequestToTodo(todoRequest)).thenReturn(updatedTodo);
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);
        when(todoMapper.todoToTodoResponse(updatedTodo)).thenReturn(expectedResponse);

        TodoResponse actualResponse = todoService.editTodo(todoId, todoRequest);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(todoId, actualResponse.getId());
        verify(todoRepository, times(1)).findByIdAndUserId(todoId, userId);
        verify(todoRepository, times(1)).save(updatedTodo);
        verify(todoMapper, times(1)).todoToTodoResponse(updatedTodo);
    }

    @Test
    void testRemoveTodo_Success() {
        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setUserId(userId);

        when(todoRepository.findByIdAndUserId(todoId, userId)).thenReturn(Optional.of(existingTodo));

        todoService.removeTodo(userId, todoId);

        verify(todoRepository, times(1)).findByIdAndUserId(todoId, userId);
        verify(todoRepository, times(1)).delete(existingTodo);
    }

    @Test
    void testGetUserTodo_Success() {

        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setUserId(userId);

        TodoResponse expectedResponse = new TodoResponse();
        expectedResponse.setId(todoId);

        when(todoRepository.findByIdAndUserId(todoId, userId)).thenReturn(Optional.of(existingTodo));
        when(todoMapper.todoToTodoResponse(existingTodo)).thenReturn(expectedResponse);

        TodoResponse actualResponse = todoService.getUserTodo(userId, todoId);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(todoId, actualResponse.getId());
        verify(todoRepository, times(1)).findByIdAndUserId(todoId, userId);
        verify(todoMapper, times(1)).todoToTodoResponse(existingTodo);
    }

    @Test
    void testTodoComplete_Success() {
        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setCompleted(false);

        Todo completedTodo = new Todo();
        completedTodo.setId(todoId);
        completedTodo.setCompleted(true);

        TodoResponse expectedResponse = new TodoResponse();
        expectedResponse.setId(todoId);
        expectedResponse.setCompleted(true);

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(completedTodo);
        when(todoMapper.todoToTodoResponse(completedTodo)).thenReturn(expectedResponse);

        TodoResponse actualResponse = todoService.todoComplete(todoId);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertTrue(actualResponse.getCompleted());
        verify(todoRepository, times(1)).findById(todoId);
        verify(todoRepository, times(1)).save(completedTodo);
        verify(todoMapper, times(1)).todoToTodoResponse(completedTodo);
    }

    @Test
    void testGetUserTodoList_Success() {

        Todo todo1 = new Todo();
        todo1.setId("todo1");

        Todo todo2 = new Todo();
        todo2.setId("todo2");

        List<Todo> todoList = List.of(todo1, todo2);

        TodoResponse response1 = new TodoResponse();
        response1.setId("todo1");

        TodoResponse response2 = new TodoResponse();
        response2.setId("todo2");

        when(todoRepository.findAllByUserId(userId)).thenReturn(todoList);
        when(todoMapper.todoToTodoResponse(todo1)).thenReturn(response1);
        when(todoMapper.todoToTodoResponse(todo2)).thenReturn(response2);

        List<TodoResponse> actualResponseList = todoService.getUserTodoList(userId);

        Assertions.assertNotNull(actualResponseList);
        Assertions.assertEquals(2, actualResponseList.size());
        verify(todoRepository, times(1)).findAllByUserId(userId);
        verify(todoMapper, times(1)).todoToTodoResponse(todo1);
        verify(todoMapper, times(1)).todoToTodoResponse(todo2);
    }

}
