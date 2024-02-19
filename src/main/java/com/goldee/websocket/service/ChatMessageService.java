package com.goldee.websocket.service;

import java.util.List;

import com.goldee.websocket.entity.ChatMessage;

public interface ChatMessageService {
	 public ChatMessage save(ChatMessage chatMessage);
	 public List<ChatMessage> findChatMessages(String senderId, String recipientId);
}
