package com.goldee.websocket.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.JsonNode;
import com.goldee.websocket.entity.ChatMessage;
import com.goldee.websocket.payloads.ChatNotification;
import com.goldee.websocket.service.ChatMessageService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

        private final SimpMessagingTemplate messagingTemplate;
        private final ChatMessageService chatMessageService;

        @SuppressWarnings("null")
        @MessageMapping("/chat")
        public void processMessage(@Payload JsonNode chatMessage) {
                String senderId = chatMessage.get("senderId").asText();
                String recipientId = chatMessage.get("recipientId").asText();
                System.out.println("this method is called");
                String content = chatMessage.get("content").asText();
                Date timestamp = new Date();
                ChatMessage message = ChatMessage.builder()
                                .senderId(senderId)
                                .recipientId(recipientId)
                                .content(content)
                                .timestamp(timestamp)
                                .build();
                ChatMessage savedMsg = chatMessageService.save(message);
                messagingTemplate.convertAndSendToUser(
                                message.getRecipientId(), "/queue/messages",
                                new ChatNotification(
                                                savedMsg.getId(),
                                                savedMsg.getSenderId(),
                                                savedMsg.getRecipientId(),
                                                savedMsg.getContent()));
        }

        @GetMapping("/messages/{senderId}/{recipientId}")
        public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                        @PathVariable String recipientId) {
                return ResponseEntity
                                .ok(chatMessageService.findChatMessages(senderId, recipientId));
        }
}
