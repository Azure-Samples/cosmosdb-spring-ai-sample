package com.microsoft.azure.spring.chatgpt.sample.cli;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public DocumentIndexPlanner planner(EmbeddingModel openAIClient, VectorStore vectorStore) {
        return new DocumentIndexPlanner(openAIClient, vectorStore);
    }

    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

}
