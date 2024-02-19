package com.goldee.websocket.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldee.websocket.entity.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	 Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
