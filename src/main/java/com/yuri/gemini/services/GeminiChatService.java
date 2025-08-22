package com.yuri.gemini.services;

import com.yuri.gemini.dto.PromptRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Service
@SessionScope
public class GeminiChatService implements ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public GeminiChatService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @Override
    public String getAnswer(PromptRequest prompt) {

        String conversationId = prompt.conversationId() == null ? UUID.randomUUID().toString() : prompt.conversationId();

        return chatClient.prompt()
                .advisors(VectorStoreChatMemoryAdvisor.builder(vectorStore).build(),
                                QuestionAnswerAdvisor.builder(vectorStore).build())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(userMessage -> userMessage.text(prompt.prompt()))
                .call()
                .content();
    }
}
