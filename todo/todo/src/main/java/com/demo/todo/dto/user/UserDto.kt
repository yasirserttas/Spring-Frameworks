package com.demo.todo.dto.user

import com.demo.todo.dto.task.TaskDto
import com.demo.todo.dto.task.UserTaskDto
import java.util.UUID

data class UserDto(

    val id:UUID,
    val name:String,
    val surName:String,
    val userName:String,
    val password:String,
    val tasks:Set<UserTaskDto>?,

    
)
