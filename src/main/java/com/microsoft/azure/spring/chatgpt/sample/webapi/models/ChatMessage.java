package com.microsoft.azure.spring.chatgpt.sample.webapi.models;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import java.util.Map;

public class ChatMessage implements Message {
    private String role;
    private String content;

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Map.of();
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.USER;
    }
}
