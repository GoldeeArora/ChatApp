package com.goldee.websocket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldee.websocket.entity.ChatMessage;
import java.util.*;
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	List<ChatMessage> findByChatId(String chatId);
}
