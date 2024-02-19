package com.goldee.websocket.service.impl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goldee.websocket.config.JWTService;
import com.goldee.websocket.entity.Status;
import com.goldee.websocket.entity.User;
import com.goldee.websocket.payloads.AuthResponse;
import com.goldee.websocket.payloads.LoginDto;
import com.goldee.websocket.payloads.UserDto;
import com.goldee.websocket.payloads.UserDto2;
import com.goldee.websocket.repositories.UserRepository;
import com.goldee.websocket.service.AuthenticationService;
import com.goldee.websocket.service.UserService;

@Service

public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	private final String folderPath = "C:\\springbootprojects\\websocket\\src\\main\\resources\\static\\";

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private UserService userService;

	@SuppressWarnings("null")
	@Override
	public AuthResponse register(UserDto userDto) {
		MultipartFile file = userDto.getImage();
		String filePath = folderPath + userDto.getEmail() + ".jpg";
		User user = this.dtoToUser(userDto);
		user.setStatus(Status.ONLINE);
		String jwtToken = "";
		user.setPassword(encoder.encode(userDto.getPassword()));
		try {
			if (this.userRepo.existsById(user.getEmail())) {
				return new AuthResponse("", null, "User Already Exists");
			}
			user.setImagePath(filePath);
			User user2 = userRepo.save(user);
			UserDto2 userDto2 = this.userToDto(user2);
			file.transferTo(new File(filePath));
			jwtToken = jwtService.generateToken(user);
			messagingTemplate.convertAndSend("/broadcast/listOfUsers", userService.getAllusers());
			return new AuthResponse(jwtToken, userDto2, "User has been successfully created");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuthResponse("", null, "Couldn't save data");

		}

	}

	public List<UserDto2> getData() {
		return userService.getAllusers();

	}

	@SuppressWarnings("null")
	@Override
	public AuthResponse login(LoginDto request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = null;

		user = userRepo.findById(request.getEmail()).orElse(null);
		System.out.println(user);

		if (user == null) {
			return new AuthResponse("", null, "Please enter correct email and password");
		}
		user.setStatus(Status.ONLINE);
		userRepo.save(user);
		UserDto2 userDto = this.modelMapper.map(user, UserDto2.class);
		var jwtToken = jwtService.generateToken(user);
		return new AuthResponse(jwtToken, userDto, "User has logged in");

	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		// User user = new User();
		// user.setEmail(userDto.getEmail());
		// user.setFullname(userDto.getFullName());
		return user;
	}

	private UserDto2 userToDto(User user) {
		UserDto2 userDto = this.modelMapper.map(user, UserDto2.class);
		return userDto;
	}

	public List<UserDto2> getList(String email) {
		List<User> userList = userRepo.findAll();
		List<UserDto2> list = userList.stream()
				.map(this::userToDto)
				.collect(Collectors.toList());
		// System.out.println(list);
		return list;
	}

}
