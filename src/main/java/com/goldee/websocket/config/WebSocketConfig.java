package com.goldee.websocket.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(@SuppressWarnings("null") MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/broadcast", "/user");
		registry.setApplicationDestinationPrefixes("/app");
		// registry.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(@SuppressWarnings("null") StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
				.setAllowedOriginPatterns("*")
				// .setAllowedOriginPatterns("*")
				.withSockJS();

	}

	@Override
	public boolean configureMessageConverters(@SuppressWarnings("null") List<MessageConverter> messageConverters) {
		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		converter.setContentTypeResolver(resolver);
		messageConverters.add(converter);

		return false;
	}

	// @Bean
	// public SimpMessagingTemplate brokerMessagingTemplate() {
	// SimpMessagingTemplate template = new SimpMessagingTemplate(brokerChannel());
	// String prefix = getBrokerRegistry().getUserDestinationPrefix();
	// if (prefix != null) {
	// template.setUserDestinationPrefix(prefix);
	// }

	// @Bean
	// public SimpAnnotationMethodMessageHandler
	// simpAnnotationMethodMessageHandler() {
	// SimpAnnotationMethodMessageHandler handler =
	// simpAnnotationMethodMessageHandler();
	// handler.setDestinationPrefixes(getBrokerRegistry().getApplicationDestinationPrefixes());

	// // @Bean
	// // public SimpMessagingTemplate messagingTemplate() {
	// // return new SimpMessagingTemplate();
	// // }
}
