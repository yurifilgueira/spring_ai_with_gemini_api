package com.yuri.gemini.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        var systemPrompt = "Você é um assistente para tarefas do dia a dia.";
        return chatClientBuilder
                .defaultSystem(systemPrompt)
                .build();
    }

}
