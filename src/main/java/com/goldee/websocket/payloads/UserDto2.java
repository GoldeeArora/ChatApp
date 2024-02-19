package com.goldee.websocket.payloads;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.goldee.websocket.entity.*;
@Getter
@Setter
@RequiredArgsConstructor
public class UserDto2 {
	private String fullName;
	private String email;
	private Status Status;
}