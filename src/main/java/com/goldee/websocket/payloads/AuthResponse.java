package com.goldee.websocket.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse{
  private String token;
  private UserDto2 user;
  private String message;
  
}
