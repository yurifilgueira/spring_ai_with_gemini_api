package com.yuri.gemini.controllers;

import com.yuri.gemini.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/prompt")
public class PromptController {

    private final ChatService chatService;

    @Autowired
    public PromptController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping()
    public String prompt(@RequestParam(value = "question") String question) {
        return chatService.getAnswer(question);
    }

}
