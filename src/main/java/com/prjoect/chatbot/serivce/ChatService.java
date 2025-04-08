package com.prjoect.chatbot.serivce;

import com.prjoect.chatbot.Dto.QueryRequest;
import com.prjoect.chatbot.Dto.QueryResponse;
import com.prjoect.chatbot.Dto.QueryResultDTO;
//import com.prjoect.chatbot.model.ChatHistory;
//import com.prjoect.chatbot.repository.ChatHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service

public class ChatService {
    private final WebClient webClient;
    private final JdbcTemplate jdbcTemplate;

//    private final ChatHistoryRepository chatHistoryRepository;

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    public ChatService(WebClient.Builder webClientBuilder, JdbcTemplate jdbcTemplate) {
        this.webClient = webClientBuilder.baseUrl("http://52.231.104.155:8000").build();  // FastAPI 서버 주소
        this.jdbcTemplate = jdbcTemplate;
    }


    public Mono<String> getSqlByFastAPI(String input, String sessionId) {
        return webClient.post()
                .uri("/generate-sql")  // FastAPI 엔드포인트
                .bodyValue(new QueryRequest(input, sessionId))  // 요청 JSON 바디
                .retrieve()
                .bodyToMono(QueryResponse.class)  // FastAPI 응답 매핑
                .map(response -> response.getSql());  // SQL 문자열 추출
    }

    public Mono<QueryResultDTO> getAsyncTest(String question, int page, int size, String sessionId) {
        int offset = page * size;

//        ChatHistory chatHistory = ChatHistory.builder()
//                .sessionId(sessionId)
//                .userMessage(question)
//                .build();

        return getSqlByFastAPI(question, sessionId)
                .publishOn(Schedulers.boundedElastic())
                .map(sql -> {
                    try {
                        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
//                        if (sql.contains("LIMIT") || sql.contains("limit")) {
//                            res = jdbcTemplate.queryForList(sql);
//                        } else {
//                            sql += " LIMIT ? OFFSET ?";
//                            res = jdbcTemplate.queryForList(sql, size, offset);
//                        }

//                        chatHistoryRepository.save(chatHistory);
                        return new QueryResultDTO(sql, res);
                    } catch (Exception e) {
                        String timestamp = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                        // 로그로 모든 정보 출력
                        log.error("===== SQL 실행 중 예외 발생 =====");
                        log.error(" time: {}", timestamp);
                        log.error(" question(input): {}", question);
                        log.error(" SQL: {}", sql);
                        log.error(" ex: {}", e.getMessage());
                        log.error("================================");

                        throw e;
                    }
                });
    }

    public Mono<QueryResultDTO> getAsync(String question, int page, int size){

        String sql = "SELECT * FROM products LIMIT ?, ?";

        int offset = page * size;

        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, offset, size);

        return Mono.just(new QueryResultDTO(sql, res));
    }

    public QueryResultDTO getSqlResult(QueryRequest queryRequest){
        List<Map<String, Object>> res = jdbcTemplate.queryForList(queryRequest.getInput());
        return new QueryResultDTO(queryRequest.getInput(), res);
    }

}
