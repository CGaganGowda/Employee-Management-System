package com.manage.employee.controller;

import com.manage.employee.dto.LoginDto;
import com.manage.employee.dto.UserDto;
import com.manage.employee.model.User;
import com.manage.employee.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    public AuthService authService;

    @PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto){
        User user = authService.register(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto  loginDto){
       JwtAuthResponse respone = new JwtAuthResponse();
        String token = authService.login(loginDto);
        response.setToken(token);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

