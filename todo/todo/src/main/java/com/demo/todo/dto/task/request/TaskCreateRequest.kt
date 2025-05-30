package com.demo.todo.dto.task.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class TaskCreateRequest(

    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 1, max = 85 , message = "Başlık 1 ila 85 karakter arası olmalıdır")
    val title:String,
    @Size(max = 500, message = "Başlık 500 den çok olamaz")
    val description:String?,

    val completed: Boolean,
    )
