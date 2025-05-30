package com.demo.todo.entity

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(name = "app_user")
data class User(

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    val id : UUID? = null,

    @Column(name= "name")
    val name : String ,

    @Column(name = "sur_name")
    val surName:  String,

    @Column(name = "user_name")
    val userName : String,

    @Column(name = "password")
    val password : String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL] , fetch = FetchType.LAZY)
    val tasks:Set<Task> = HashSet(),

    @Column(name = "account_non_expired", columnDefinition = "BOOLEAN DEFAULT TRUE")
    val accountNonExpired: Boolean = true,

    @Column(name = "account_non_locked", columnDefinition = "BOOLEAN DEFAULT TRUE")
    val accountNonLocked: Boolean = true,

    @Column(name = "credentials_non_expired", columnDefinition = "BOOLEAN DEFAULT TRUE")
    val credentialsNonExpired: Boolean = true,

    @Column(name = "enabled", columnDefinition = "BOOLEAN DEFAULT TRUE")
    val enabled: Boolean = true,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_app_roles", //
        joinColumns = [JoinColumn(name = "user_id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val roles: Set<Role> = HashSet(),



) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
