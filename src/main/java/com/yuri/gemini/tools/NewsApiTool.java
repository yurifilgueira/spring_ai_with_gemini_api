package com.yuri.gemini.tools;

import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class NewsApiTool {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NewsApiTool.class);
    @Value(value = "${news-api.key}")
    public String apiKey;
    @Value(value = "${news-api.base-url}")
    public String baseUrl;
    public RestTemplate restTemplate;

    public NewsApiTool(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Tool(name = "getAllArticlesAboutFromTo",
    description = "Get all articles mentioning a given subject between two dates.")
    public String getAllArticlesAboutFromTo(@ToolParam(description = "The given subject that the must contain") String subject,
                                            @ToolParam(description = "The minimal date that the articles must have being published. Format: yyyy-mm-dd") String from,
                                            @ToolParam(description = "The maximal date that the articles must have being published. Format: yyyy-mm-dd") String to) {

        String uri = baseUrl + "/everything" + "?q=" + subject + "&from=" + from + "&to=" + to + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(uri, String.class);

        return response;
    }

}
