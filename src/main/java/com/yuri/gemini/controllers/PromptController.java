package com.yuri.gemini.controllers;

import com.yuri.gemini.dto.PromptRequest;
import com.yuri.gemini.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/chat")
public class PromptController {

    private final ChatService chatService;

    @Autowired
    public PromptController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping()
    public String prompt(@RequestBody PromptRequest prompt) {
        return chatService.getAnswer(prompt);
    }
}
