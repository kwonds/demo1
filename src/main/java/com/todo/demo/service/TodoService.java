package com.todo.demo.service;

import com.todo.demo.dto.TodoInfoRequest;
import com.todo.demo.dto.TodoRequest;
import com.todo.demo.dto.TodoSearchRequest;
import com.todo.demo.model.Todo;
import com.todo.demo.model.User;
import com.todo.demo.repository.TodoRepository;
import com.todo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserValidator userValidator;
    private final TodoRepository todoRepository;

    public void createTodo(String userId, TodoRequest request) {
        User user = userValidator.activeUserByUserId(userId);
        Todo todo = Todo.of(request, user.getId());
        todoRepository.save(todo);
    }

    public List<Todo> getAllTodos(String userId) {
        User user = userValidator.activeUserByUserId(userId);
        return todoRepository.findAllByWriterIdOrderByDueDateAsc(user.getId());
    }

    public Todo getTodoById(String userId, Long todoId) {
        User user = userValidator.activeUserByUserId(userId);
        return todoRepository.findByWriterIdAndId(user.getId(), todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일이 존재하지 않습니다."));
    }

    public void updateTodo(String userId, Long todoId, TodoInfoRequest request) {
        Todo todo = getTodoById(userId, todoId);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setDueDate(request.getDueDate());
        todoRepository.save(todo);
    }

    public void deleteTodo(String userId, Long id) {
        getTodoById(userId, id);
        todoRepository.deleteById(id);
    }

    public List<Todo> searchTodos(String userId, TodoSearchRequest searchRequest) {
        User user = userValidator.activeUserByUserId(userId);
        return todoRepository.searchTodos(user.getId(), searchRequest);
    }
}
