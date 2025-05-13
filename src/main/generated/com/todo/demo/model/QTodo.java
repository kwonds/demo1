package com.todo.demo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTodo is a Querydsl query type for Todo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodo extends EntityPathBase<Todo> {

    private static final long serialVersionUID = -311586405L;

    public static final QTodo todo = new QTodo("todo");

    public final BooleanPath completed = createBoolean("completed");

    public final StringPath description = createString("description");

    public final StringPath dueDate = createString("dueDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath registDate = createString("registDate");

    public final StringPath title = createString("title");

    public final StringPath updateDate = createString("updateDate");

    public final NumberPath<Long> writerId = createNumber("writerId", Long.class);

    public QTodo(String variable) {
        super(Todo.class, forVariable(variable));
    }

    public QTodo(Path<? extends Todo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTodo(PathMetadata metadata) {
        super(Todo.class, metadata);
    }

}

