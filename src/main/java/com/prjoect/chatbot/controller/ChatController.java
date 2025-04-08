package com.prjoect.chatbot.controller;

import com.prjoect.chatbot.Dto.QueryRequest;
import com.prjoect.chatbot.Dto.QueryResultDTO;
import com.prjoect.chatbot.Dto.RequestDTO;
import com.prjoect.chatbot.serivce.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

//    @PostMapping()
//    public QueryResultDTO getSync(@RequestBody String question) {
//        return chatService.temp(question);
//    }

    @PostMapping("/async")
    public Mono<QueryResultDTO> getASync(@RequestBody RequestDTO requestDTO,  @RequestHeader("X-Session-Id") String sessionId) {
        return chatService.getAsyncTest(requestDTO.getInput(), requestDTO.getPage(), requestDTO.getSize(), sessionId)
                .doOnNext(re9lt -> System.out.println("Response Thread: " + Thread.currentThread().getName()));
    }

//    @GetMapping("/")
//    public Mono<QueryResultDTO> getChatHistory(@RequestHeader("X-Session-Id") String sessionId) {
//
//    }

    @PostMapping("/sql")
    public QueryResultDTO getSQLTest(@RequestBody QueryRequest queryRequest ) {
        return chatService.getSqlResult(queryRequest);
    }

}
