package com.codenomads.userservice.controller;

import com.codenomads.userservice.dto.UserDto;
import com.codenomads.userservice.dto.UserDtoMapper;
import com.codenomads.userservice.model.User;
import com.codenomads.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    // GET http://localhost:8080/user/hello@gmail.com
    @GetMapping("{email}")
    public UserDto getUserByEmail(@PathVariable
                                  @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                                          String email) {

        User user = userService.getUserByEmail(email);

        return userDtoMapper.toDto(user);
    }

    @GetMapping
    public Page<UserDto> getAllUserByPage(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return userService.getAllUsersByPage(pageable)
                .map(userDtoMapper::toDto);
    }

    @GetMapping("/asList")
    public List<UserDto> getAllUsersAsList(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return userService.getAllUsersByPage(pageable)
                .map(userDtoMapper::toDto)
                .toList();
    }

    @GetMapping("olderThan/{age}")
    public Page<UserDto> getAllUsersEqualToOrOlderThan(@PageableDefault(size = 20, page = 0) Pageable pageable, @PathVariable int age) {
        return userService.getAllUsersEqualToOrOlderThan(age)
                .map(userDtoMapper::toDto);
    }

    @GetMapping("youngerThan/{age}")
    public Page<UserDto> getAllUsersYoungerThan(@PageableDefault(size = 20, page = 0) Pageable pageable, @PathVariable int age) {
        return userService.getAllUsersYoungerThan(age)
                .map(userDtoMapper::toDto);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userDtoMapper.toUser(userDto);
        User savedUser = userService.createUser(user);
        return userDtoMapper.toDto(savedUser);
    }

    @PutMapping
    public UserDto createOrUpdateUser(@RequestBody UserDto userDto) {
        User user = userDtoMapper.toUser(userDto);
        User savedUser = userService.createOrUpdateUser(user);
        return userDtoMapper.toDto(savedUser);
    }

    @DeleteMapping("{email}")
    public void deleteUserByEmail(@PathVariable
                                  @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                                  String email) {
        userService.deleteUserByEmail(email);
    }
}
