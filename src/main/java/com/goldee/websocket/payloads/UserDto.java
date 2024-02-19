package com.goldee.websocket.payloads;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private String fullName;
	private String email;
	private String password;
	private MultipartFile image;
}
