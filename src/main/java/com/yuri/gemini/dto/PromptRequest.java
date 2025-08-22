package com.yuri.gemini.dto;

import javax.validation.constraints.Null;

public record PromptRequest(String user, String prompt, String conversationId) {
}
