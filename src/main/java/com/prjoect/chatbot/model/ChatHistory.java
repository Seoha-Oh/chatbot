package com.prjoect.chatbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "chat_history")
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class ChatHistory {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String sessionId;
//
//    @Column(columnDefinition = "TEXT")
//    private String userMessage;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//}