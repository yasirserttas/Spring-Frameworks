package com.demo.todo.dto.task

import java.util.UUID

data class UserTaskDto(
    val id: UUID,
    val title: String,
    val completed: Boolean
)
