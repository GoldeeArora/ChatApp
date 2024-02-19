package com.goldee.websocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldee.websocket.payloads.AuthResponse;
import com.goldee.websocket.payloads.LoginDto;
import com.goldee.websocket.payloads.UserDto;
import com.goldee.websocket.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(
			@ModelAttribute UserDto request) {

		return ResponseEntity.ok(authenticationService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@RequestBody LoginDto request) {
		return ResponseEntity.ok(authenticationService.login(request));
	}

}
