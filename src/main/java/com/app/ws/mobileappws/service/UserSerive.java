package com.app.ws.mobileappws.service;

import com.app.ws.mobileappws.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserSerive extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUser(String userId);

    List<UserDto> getUsers();

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);

    UserDto QueryUserEntityByUserId(String userId);

    List<UserDto> getPage(int page, int limit);
}

