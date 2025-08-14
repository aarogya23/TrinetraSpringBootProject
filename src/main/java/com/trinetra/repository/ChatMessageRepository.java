package com.trinetra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinetra.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByOrderByCreatedAtDesc();
    List<ChatMessage> findTop10ByOrderByCreatedAtDesc();
}
