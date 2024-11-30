package com.microsoft.azure.spring.chatgpt.sample.webapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.spring.chatgpt.sample.webapi.models.ChatMessage;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private static final String prompt = """
            Context information is below.
            ---------------------
            %s
            ---------------------
            Given the context information and not prior knowledge, answer the question: %s
            """;

    @Autowired
    @Lazy
    private VectorStore vectorStore;

    @Autowired
    @Lazy
    private ChatClient chatClient;

    @PostMapping("/completions")
    public String generation(@RequestBody String userInput) throws JsonProcessingException {
        List<Message> messages = getMessages(userInput);
        String response = this.chatClient.prompt(prompt)
                .user(messages.get(0).getContent())
                .messages(messages)
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.query(messages.get(0).getContent()).withTopK(5)))
                .call()
                .content();
        return response;
    }

    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

    public List<Message> getMessages(String text) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return StreamSupport.stream(objectMapper.readTree(text).get("messages").spliterator(), false)
                .map(node -> new ChatMessage(node.get("role").asText(), node.get("content").asText()))
                .collect(Collectors.toList());
    }


}
