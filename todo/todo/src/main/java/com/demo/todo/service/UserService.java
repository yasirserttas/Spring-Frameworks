package com.demo.todo.service;

import com.demo.todo.config.PasswordEncoderConfig;
import com.demo.todo.converter.UserConverter;
import com.demo.todo.dto.user.UserDto;
import com.demo.todo.dto.user.request.CreateUserRequest;
import com.demo.todo.dto.user.request.UpdateUserRequest;
import com.demo.todo.entity.User;
import com.demo.todo.exception.UserNotFoundException;
import com.demo.todo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoderConfig passwordEncoderConfig;


    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordEncoderConfig passwordEncoderConfig) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto createdUser(CreateUserRequest createUserRequest){
        User user =new User(null,
                createUserRequest.getName(),
                createUserRequest.getSurName(),
                createUserRequest.getUserName(),
                passwordEncoderConfig.bCryptPasswordEncoder().encode(createUserRequest.getPassword()),
                new HashSet<>(),
                true,
                true,
                true,
                true,
                createUserRequest.getRoles()
                );

        return userConverter.convertToUser(userRepository.save(user));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto updateUser(UpdateUserRequest updateUserRequest , UUID userId){

        User existingUser = getUserById(userId);

        User updateUser = new User(
                existingUser.getId(),
                updateUserRequest.getName(),
                updateUserRequest.getSurName(),
                updateUserRequest.getUserName(),
                passwordEncoderConfig.bCryptPasswordEncoder().encode(updateUserRequest.getPassword()),
                existingUser.getTasks(),
                existingUser.getAccountNonExpired(),
                existingUser.getAccountNonLocked(),
                existingUser.getCredentialsNonExpired(),
                existingUser.getEnabled(),
                existingUser.getRoles()
        ) ;

        return userConverter.convertToUser(userRepository.save(updateUser));

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(UUID userId){

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(UUID userId){

        return   userConverter.convertToUser(userRepository.
                findById(userId).orElseThrow(()->
                        new UserNotFoundException("The user with this id was not found: "+userId))) ;


    }

    @Transactional(readOnly = true)
    protected User getUserById(UUID userId){

        return   userRepository.
                findById(userId).orElseThrow(()->
                        new UserNotFoundException("The user with this id was not found: "+userId));


    }





}
