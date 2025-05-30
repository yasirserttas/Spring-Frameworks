package com.demo.todo.service;

import com.demo.todo.converter.TaskConverter;
import com.demo.todo.dto.task.TaskDto;
import com.demo.todo.dto.task.request.TaskCreateRequest;
import com.demo.todo.dto.task.request.TaskUpdateRequest;
import com.demo.todo.entity.Task;
import com.demo.todo.entity.User;
import com.demo.todo.exception.TaskNotFoundException;
import com.demo.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository,TaskConverter taskConverter,UserService userService) {
        this.taskConverter = taskConverter;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public  TaskDto createTask(TaskCreateRequest taskCreateRequest , UUID userId){

        User user = userService.getUserById(userId);
        Task task = new Task(
                null,
                taskCreateRequest.getTitle(),
                taskCreateRequest.getDescription(),
                taskCreateRequest.getCompleted(),
                user
        );

        return taskConverter.convertToTask(taskRepository.save(task));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskDto updateTask(UUID taskId , TaskUpdateRequest updateRequest){

        Task existingTask = getTaskById(taskId);

        Task updateTask= existingTask.copy(existingTask.getId(),
                updateRequest.getTitle(),
                updateRequest.getDescription(),
                updateRequest.getCompleted(),
                existingTask.getUser());

        return taskConverter.convertToTask(taskRepository.save(updateTask));

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTask(UUID taskId){

        taskRepository.deleteById(taskId);

    }

    @Transactional(readOnly = true)
    public TaskDto getTaskDtoById(UUID taskId){

        return  taskConverter.convertToTask(taskRepository.findById(taskId).
                orElseThrow(()->new TaskNotFoundException
                        ("The task with this id was not found: "+taskId))) ;

    }

    @Transactional(readOnly = true)
    protected Task getTaskById(UUID taskId){

        return  taskRepository.findById(taskId).
                orElseThrow(()->new TaskNotFoundException
                        ("The task with this id was not found: "+taskId)) ;

    }

    @Transactional(readOnly = true)
    public Set<TaskDto> getTasksByUserId(UUID userId){

        return taskConverter.convertToTaskSet(taskRepository.getTasksByUserId(userId)) ;
    }


}
