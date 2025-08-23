package com.yuri.gemini.tools;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeTool {

    @Tool(name = "currentlyDateTime", description = "Use this function to get the currently date and time.")
    public String currentlyDateTime() {
        return LocalDateTime.now().toString();

    }
}
