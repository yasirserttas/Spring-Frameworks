package com.demo.todo.entity

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
data class Task(

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    val id : UUID? = null,

    @Column(name = "title")
    val title:String,

    @Column(name = "description")
    val description:String?,

    @Column(name = "completed" )
    val completed: Boolean= false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="app_user_id")
    val user:User,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}


