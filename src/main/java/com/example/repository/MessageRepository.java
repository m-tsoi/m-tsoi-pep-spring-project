package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository interface for the Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Long>{
    void deleteByMessageId(Integer messageId);

    Optional<Message> findByMessageId(Integer messageId);

    List<Message> findByPostedBy(int postedBy);
}
