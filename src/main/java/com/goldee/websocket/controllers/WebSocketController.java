package com.goldee.websocket.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.goldee.websocket.entity.Status;
import com.goldee.websocket.entity.User;
import com.goldee.websocket.payloads.UserDto2;
import com.goldee.websocket.repositories.UserRepository;
import com.goldee.websocket.service.UserService;

@Controller
public class WebSocketController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/new-joined")
    @SendTo("/broadcast/listOfUsers")
    public ResponseEntity<List<UserDto2>> listOfUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllusers());
    }

    @MessageMapping("/disconnect")
    @SendTo("/broadcast/listOfUsers")
    public ResponseEntity<List<UserDto2>> disconnectUser(@Payload JsonNode payload) throws Exception {

        try {
            String userId = payload.get("userId").asText();
            System.out.println(userId);
            @SuppressWarnings("null")
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setStatus(Status.OFFLINE);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(userService.getAllusers());
    }
}
