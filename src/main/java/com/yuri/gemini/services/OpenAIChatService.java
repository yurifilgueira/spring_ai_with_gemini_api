package com.yuri.gemini.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Service
@SessionScope
public class OpenAIChatService implements ChatService {

    private final ChatClient chatClient;
    private final String conversetionId;

    public OpenAIChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.conversetionId = UUID.randomUUID().toString();
    }

    @Override
    public String getAnswer(String prompt) {
        return chatClient.prompt()
                .user(userMessage -> userMessage.text(prompt))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversetionId))
                .call()
                .content();
    }
}
