package com.example.authorserver.controller;


import com.example.authorserver.dto.UserDto;
import com.example.authorserver.model.User;
import com.example.authorserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createuser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.OK);
    }
    @PostMapping("/read/{usernameOrEmail}")
    public ResponseEntity<User> readbyusernameoremail(@PathVariable String usernameOrEmail){
        return new ResponseEntity<>(userService.readbyusername(usernameOrEmail), HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<User> updateuser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
    }
    @PostMapping("/delete/{usernameoremail}")
    public ResponseEntity<String> deletebyusernameoremail(@PathVariable String usernameoremail){
        return new ResponseEntity<>(userService.deletebyusername(usernameoremail), HttpStatus.OK);
    }
}
