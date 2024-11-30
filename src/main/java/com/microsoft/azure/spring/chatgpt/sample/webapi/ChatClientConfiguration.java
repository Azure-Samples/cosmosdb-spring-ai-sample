package com.microsoft.azure.spring.chatgpt.sample.webapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;

@Configuration
public class ChatClientConfiguration {

    private final ChatClient.Builder chatClientBuilder;

    public ChatClientConfiguration(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }

    @Bean
    public ChatClient chatClient() {
        return chatClientBuilder.build();
    }
}

