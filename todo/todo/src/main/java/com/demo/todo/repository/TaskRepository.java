package com.demo.todo.repository;

import com.demo.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Set<Task> getTasksByUserId(UUID userId);
}
