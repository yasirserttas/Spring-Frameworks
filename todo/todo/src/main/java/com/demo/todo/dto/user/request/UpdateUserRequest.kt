package com.demo.todo.dto.user.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    @NotBlank(message = "isim boş olamaz")
    @Size(min = 2 , max = 40, message = "isim 2 ila 40 karakter arasında olmalıdır")
    val name:String,
    @NotBlank(message = "soyisim boş olamaz")
    @Size(min = 2 , max = 20, message = "soyisim 2 ila 20 karakter arasında olmalıdır")
    val surName:String,
    @NotBlank(message = "kullanıcıadı boş olamaz")
    @Size(min = 4 , max = 20, message = "kullanıcıadı 4 ila 20karakter arasında olmalıdır")
    val userName:String,
    @NotBlank(message = "şifre boş olamaz")
    @Size(min = 8 ,max= 100 )
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).*$",
        message = "Şifre en az bir büyük harf, bir küçük harf ve bir noktalama işareti içermelidir"
    )
    val password:String,
)
