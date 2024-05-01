package com.example.authorserver.service;

import com.example.authorserver.dto.UserDto;
import com.example.authorserver.model.User;

public interface UserService {
    User create(UserDto userDto);
    User readbyusername(String username);
    User update(UserDto userDto);
    String deletebyusername(String username);
}
