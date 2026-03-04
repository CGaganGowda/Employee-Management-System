package com.manage.employee.service;

import com.manage.employee.dto.LoginDto;
import com.manage.employeedto.UserDto;
import com.manage.employee.exception.TodoApiException;
import com.manage.employee.model.Role;
import com.manage.employee.model.User;
import com.manage.employee.repo.RoleRepo;
import com.manage.employee.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User register(UserDto userDto) {


        if(userRepo.existsByUsername(userDto.getUsername())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if(userRepo.existsByEmail(userDto.getEmail())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByName("ROLE_USER"));

        user.setRoles(roles);

        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login successful";
    }
}

