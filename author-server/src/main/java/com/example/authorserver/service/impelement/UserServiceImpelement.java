package com.example.authorserver.service.impelement;


import com.example.authorserver.dto.UserDto;
import com.example.authorserver.model.Authority;
import com.example.authorserver.model.User;
import com.example.authorserver.repository.AuthorityRepository;
import com.example.authorserver.repository.UserRepository;
import com.example.authorserver.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpelement implements UserService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpelement(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(UserDto userDto) {
        if(userRepository.findByUsernameOrEmail(userDto.getUsername(),userDto.getEmail()).isPresent()){
            throw new RuntimeException("User already register!");
        }
        Authority authority = authorityRepository.findByName("USER").get();
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setAuthorities(authoritySet);
        userRepository.save(user);
        return user;
    }

    @Override
    public User readbyusername(String username) {
        User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(
                ()-> new RuntimeException("User not found!")
        );
        return user;
    }

    @Override
    public User update(UserDto userDto) {
        if(!userRepository.findByUsernameOrEmail(userDto.getUsername(),userDto.getEmail()).isPresent()){
            throw new RuntimeException("User not found!");
        }
        Authority authority = authorityRepository.findByName("USER").get();
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setAuthorities(authoritySet);
        userRepository.save(user);
        return user;
    }

    @Override
    public String deletebyusername(String username) {
        if(userRepository.deleteByUsernameOrEmail(username, username)){
            return "Deleting is successful!";
        } else {
            return "Can't delete this user!";
        }
    }
}
