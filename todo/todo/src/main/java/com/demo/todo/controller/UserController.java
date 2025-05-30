package com.demo.todo.controller;

import com.demo.todo.dto.user.UserDto;
import com.demo.todo.dto.user.request.CreateUserRequest;
import com.demo.todo.dto.user.request.UpdateUserRequest;
import com.demo.todo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users") // Base path for all user-related endpoints
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserDto createdUser = userService.createdUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        UserDto userDto = userService.getUserDtoById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID userId,@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserDto updatedUser = userService.updateUser(updateUserRequest, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build(); // 204 No Content is typical for successful deletions
    }
}