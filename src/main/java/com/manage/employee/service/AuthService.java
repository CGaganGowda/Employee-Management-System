package com.manage.employee.service;

import com.manage.employee.dto.LoginDto;
import com.manage.employee.dto.UserDto;
import com.manage.employee.model.User;

public interface AuthService {
    User register(UserDto userDto);
    String login(LoginDto loginDto);
}


