package com.todo.demo.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.demo.dto.TodoSearchRequest;

import com.todo.demo.model.QTodo;
import com.todo.demo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public TodoRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Todo> searchTodos(Long userId, TodoSearchRequest searchRequest) {
        QTodo todo = QTodo.todo;

        JPAQuery<Todo> query = queryFactory.selectFrom(todo)
                .where(todo.writerId.eq(userId))
                .where(todo.completed.eq(searchRequest.isCompleted()));

        if (searchRequest.getTitle() != null && !searchRequest.getTitle().trim().isEmpty()) {
            query.where(todo.title.containsIgnoreCase(searchRequest.getTitle()));
        }

        if (searchRequest.getDescription() != null && !searchRequest.getDescription().trim().isEmpty()) {
            query.where(todo.description.containsIgnoreCase(searchRequest.getDescription()));
        }

        if (searchRequest.getDueStartDate() != null && !searchRequest.getDueStartDate().trim().isEmpty()) {
            query.where(todo.dueDate.goe(searchRequest.getDueStartDate() + " 00:00:00"));
        }

        if (searchRequest.getDueEndDate() != null && !searchRequest.getDueEndDate().trim().isEmpty()) {
            query.where(todo.dueDate.loe(searchRequest.getDueEndDate() + " 23:59:59"));
        }

        return query.fetch();
    }
}
