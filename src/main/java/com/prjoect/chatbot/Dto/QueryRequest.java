package com.prjoect.chatbot.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryRequest {
    private String input;
    @JsonProperty("session_id")
    private String sessionId;
}
