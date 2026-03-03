package com.Management.todo.service;

import com.Management.todo.dto.LoginDto;
import com.Management.todo.dto.UserDto;
import com.Management.todo.model.User;

public interface AuthService {
    User register(UserDto userDto);
    String login(LoginDto loginDto);
}
