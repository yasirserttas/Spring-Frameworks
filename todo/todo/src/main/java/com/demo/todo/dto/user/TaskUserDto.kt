package com.demo.todo.dto.user

import java.util.UUID

data class TaskUserDto(
    val id: UUID,
    val userName: String,
    val password:String,
)
