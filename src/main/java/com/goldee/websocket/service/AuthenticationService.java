package com.goldee.websocket.service;

import com.goldee.websocket.payloads.AuthResponse;
import com.goldee.websocket.payloads.LoginDto;
import com.goldee.websocket.payloads.UserDto;

public interface AuthenticationService {
	public AuthResponse register(UserDto user);

	public AuthResponse login(LoginDto loginDto);
}
