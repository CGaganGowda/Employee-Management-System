package com.manage.employee.service;

import com.manage.employee.service.dto.LoginDto;
import com.manage.employee.service.dto.UserDto;
import com.manage.employee.service.model.User;

public interface AuthService {
    User register(UserDto userDto);
    String login(LoginDto loginDto);
}

