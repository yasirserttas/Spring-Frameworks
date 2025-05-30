package com.demo.todo.dto.task

import com.demo.todo.dto.user.TaskUserDto
import java.util.UUID

data class TaskDto(
    val id:UUID,
    val title:String,
    val description:String?,
    val completed: Boolean,
    val user: TaskUserDto,
)
