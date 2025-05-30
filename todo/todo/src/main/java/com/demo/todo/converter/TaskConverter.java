package com.demo.todo.converter;

import com.demo.todo.dto.task.TaskDto;
import com.demo.todo.dto.user.TaskUserDto;
import com.demo.todo.entity.Task;
import com.demo.todo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

@Component
public class TaskConverter {
    public TaskDto convertToTask(Task from) {
        if (from == null || from.getId() == null) {
            return null;
        }

        User userEntity = from.getUser();
        TaskUserDto taskUserDto = null;


        if (userEntity == null && userEntity.getId() == null) {

            return null;

        } else {
            taskUserDto = new TaskUserDto(
                    userEntity.getId(),
                    userEntity.getUserName(),
                    userEntity.getPassword());

        }

        return new TaskDto(
                from.getId(),
                from.getTitle(),
                from.getDescription(),
                from.getCompleted(),
                taskUserDto );
    }

    public Set<TaskDto> convertToTaskSet(Set<Task> from) {
        if (from == null) {
            return Collections.emptySet();
        }
        return from.stream()
                .filter(Objects::nonNull)
                .map(this::convertToTask)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}