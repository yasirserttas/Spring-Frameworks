package com.demo.todo.controller;

import com.demo.todo.dto.task.TaskDto;
import com.demo.todo.dto.task.request.TaskCreateRequest;
import com.demo.todo.dto.task.request.TaskUpdateRequest;
import com.demo.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping
    public ResponseEntity<TaskDto> createTask
            (@RequestParam UUID userId,@Valid @RequestBody  TaskCreateRequest taskCreateRequest) {

        TaskDto createdTask = taskService.createTask(taskCreateRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);

    }


    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID taskId) {

        TaskDto taskDto = taskService.getTaskDtoById(taskId);
        return ResponseEntity.ok(taskDto);

    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask
            (@PathVariable UUID taskId,@Valid  @RequestBody TaskUpdateRequest taskUpdateRequest) {

        TaskDto updatedTask = taskService.updateTask(taskId, taskUpdateRequest);
        return ResponseEntity.ok(updatedTask);

    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {

        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/getAll")
    public ResponseEntity<Set<TaskDto>> getAllTasksForUser(@RequestParam UUID userId) {

        Set<TaskDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);

    }


}