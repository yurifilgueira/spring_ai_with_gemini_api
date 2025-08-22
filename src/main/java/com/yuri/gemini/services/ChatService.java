package com.yuri.gemini.services;

import com.yuri.gemini.dto.PromptRequest;

public interface ChatService {

    String getAnswer(PromptRequest prompt);

}
