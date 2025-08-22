package com.yuri.gemini.configs;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.HsqldbChatMemoryRepositoryDialect;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


import java.io.File;

@Configuration
public class AIChatMemoryConfig {

    private Logger log = LoggerFactory.getLogger(AIChatMemoryConfig.class);

    @Value("${spring.ai.vectorstore.simple.store.path}")
    private String vectorStorePath;

    private SimpleVectorStore  simpleVectorStoreInstance;

    @Bean
    public ChatMemoryRepository getChatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new HsqldbChatMemoryRepositoryDialect())
                .build();
    }

    @Bean
    public ChatMemory getChatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
    }

    @Bean
    public VectorStore simpleVectorStore(EmbeddingModel embeddingModel)  {
        log.info("Simple Vector Store");
        File vectorStoreFile = new File(vectorStorePath);

        var builder = SimpleVectorStore.builder(embeddingModel);
        var simpleVectorStore = builder.build();

        this.simpleVectorStoreInstance = simpleVectorStore;

        if (vectorStoreFile.exists()) {
            log.info("Loading vector store from:" + vectorStoreFile.getAbsolutePath());
            try {
                simpleVectorStoreInstance.load(vectorStoreFile);
            }catch (Exception e) {
                log.error("Error loading vector store from:" + vectorStoreFile.getAbsolutePath(), e);
            }
        }else {
            log.info("Vector store file not found or not a file, starting fresh: " +  vectorStoreFile.getAbsolutePath());
        }

        log.info("SimpleVectorStore bean created successfully");

        return simpleVectorStoreInstance;
    }

    @PreDestroy
    public void saveVectorStore() {
        if (simpleVectorStoreInstance != null) {
            File vectorStoreFile = new File(vectorStorePath);
            log.info("Attempting to save vector store to:" + vectorStoreFile.getAbsolutePath());

            try {
                simpleVectorStoreInstance.save(vectorStoreFile);
                log.info("Vector store saved successfully");
            }catch (Exception e) {
                log.error("Error saving vector store to:" + vectorStoreFile.getAbsolutePath(), e);
            }
        }else {
            log.warn("VectorStore bean was not initialized, skipping save.");
        }

    }

}
