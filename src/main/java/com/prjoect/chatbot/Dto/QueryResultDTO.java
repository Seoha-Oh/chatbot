package com.prjoect.chatbot.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResultDTO {
    private String sql;
    private List<Map<String, Object>> result;
}
