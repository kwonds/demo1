package com.todo.demo.repository;

import com.todo.demo.dto.TodoSearchRequest;
import com.todo.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
    List<Todo> findAllByWriterIdOrderByDueDateAsc(Long writerId);
    Optional<Todo> findByWriterIdAndId(Long writerId, Long todoId);
}

interface TodoRepositoryCustom {
    List<Todo> searchTodos(Long userId, TodoSearchRequest searchRequest);
}
