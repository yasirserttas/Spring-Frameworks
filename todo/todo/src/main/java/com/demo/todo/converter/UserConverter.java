package com.demo.todo.converter;

import com.demo.todo.dto.task.UserTaskDto;
import com.demo.todo.dto.user.UserDto;
import com.demo.todo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

@Component
public class UserConverter {
    public UserDto convertToUser(User from) {
        if (from == null || from.getId() == null) {
            return null;
        }

        Set<UserTaskDto> userTaskDtos; // İsim değişikliği
        if (from.getTasks() == null) {
            userTaskDtos = Collections.emptySet();
        } else {
            userTaskDtos = from.getTasks().stream()
                    .filter(task -> task != null && task.getId() != null)
                    .map(task -> new UserTaskDto(
                            task.getId(),
                            task.getTitle(),
                            task.getCompleted()
                    ))
                    .collect(Collectors.toSet());
        }

        return new UserDto(
                from.getId(),
                from.getName(),
                from.getSurName(),
                from.getUserName(),
                from.getPassword(),
                userTaskDtos
        );
    }

    public Set<UserDto> convertToUserSet(Set<User> from) {
        if (from == null) {
            return Collections.emptySet();
        }
        return from.stream()
                .filter(Objects::nonNull)
                .map(this::convertToUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}