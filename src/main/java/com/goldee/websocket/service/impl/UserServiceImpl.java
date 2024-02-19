package com.goldee.websocket.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldee.websocket.entity.User;
import com.goldee.websocket.payloads.UserDto;
import com.goldee.websocket.payloads.UserDto2;
import com.goldee.websocket.repositories.UserRepository;
import com.goldee.websocket.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto2> getAllusers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto2> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    private UserDto2 userToDto(User user) {
        UserDto2 userDto = this.modelMapper.map(user, UserDto2.class);
        return userDto;
    }

}
