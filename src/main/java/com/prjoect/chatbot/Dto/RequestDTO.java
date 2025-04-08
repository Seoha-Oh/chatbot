package com.prjoect.chatbot.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    @Schema(description = "검색할 질문", example = "SELECT * FROM products", required = true)
    private String input;

    @Schema(description = "페이지 번호 (기본값: 0)", example = "0", required = false)
    private Integer page = 0;

    @Schema(description = "페이지 크기 (기본값: 20)", example = "20", required = false)
    private Integer size = 20;
}
