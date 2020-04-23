package com.app.ws.mobileappws.service.impl;

import com.app.ws.mobileappws.data.UserEntity;
import com.app.ws.mobileappws.repository.UserRepository;
import com.app.ws.mobileappws.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserSeriveImplTest {

    @InjectMocks
    UserSeriveImpl userSerive;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
    }

    @Test
    void getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setEmail("John@gmail.com");

        when(userRepository.findUsersByUserId(anyString())).thenReturn(userEntity);
        UserDto userDto = userSerive.getUser("userId");

        assertEquals("John", userDto.getFirstName());
    }

    @Test
    void getUsers() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void loadUserByUsername() {
    }
}