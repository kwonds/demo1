package com.todo.demo.service;

import com.todo.demo.dto.TodoInfo;
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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public Todo createTodo(String userId, Todo request) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .writerId(user.getId())
                .build();
        return todoRepository.save(todo);
    }

    public List<Todo> getAllTodos(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
        List<Todo> todos = todoRepository.findAllByWriterIdOrderByIdDesc(user.getId());
        return todos;
    }

    public Todo getTodoById(String userId, Long todoId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        Todo todo = todoRepository.findByWriterIdAndIdOrderByDueDateDesc(user.getId(), todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."));

        return todo;
    }

    public Todo updateTodo(String userId, Long todoId, Todo request) {
        Todo todo = getTodoById(userId, todoId);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setDueDate(request.getDueDate());
        return todoRepository.save(todo);
    }

    public void deleteTodo(String userId, Long id) {
        getTodoById(userId, id);
        todoRepository.deleteById(id);
    }

    public List<Todo> searchTodos(String userId, TodoSearchRequest searchRequest) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        List<Todo> todos = todoRepository.searchTodos(user.getId(), searchRequest);
        return todos;
    }
}
