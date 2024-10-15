package com.hepsi.emlak.todo.controller;

import com.hepsi.emlak.todo.request.TodoRequest;
import com.hepsi.emlak.todo.response.TodoResponse;
import com.hepsi.emlak.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/todo")
@Tag(name = "todo", description = "Todo Service")
@SecurityRequirement(name = "Bearer Auth")
public class TodoController {

    private final TodoService todoService;

    @PostMapping(value = "/create")
    @Operation(summary = "Add Todo")
    public ResponseEntity<TodoResponse> addTodo(@RequestBody @Valid TodoRequest todoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.addTodo(todoRequest));
    }

    @PutMapping("/update/{todoId}")
    @Operation(summary = "Edit Todo")
    public ResponseEntity<TodoResponse> editTodo(@PathVariable String todoId, @RequestBody @Valid TodoRequest todoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.editTodo(todoId, todoRequestDTO));
    }

    @DeleteMapping("/delete/{todoId}/{userId}")
    @Operation(summary = "Remove Todo")
    public ResponseEntity<Void> deleteTodo(@PathVariable String todoId,
                                           @PathVariable String userId) {
        try {
            todoService.removeTodo(userId, todoId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/{todoId}/{userId}")
    @Operation(summary = "Get Todo")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable String todoId,
                                                @PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getUserTodo(userId, todoId));
    }

    @GetMapping("get-all/{userId}")
    @Operation(summary = "Get All Todo")
    public ResponseEntity<List<TodoResponse>> getAllUserTodo(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getUserTodoList(userId));
    }

    @PutMapping("/complete/{todoId}")
    @Operation(summary = "Complete To Todo")
    public ResponseEntity<TodoResponse> completeTodo(@PathVariable String todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.todoComplete(todoId));
    }
}
