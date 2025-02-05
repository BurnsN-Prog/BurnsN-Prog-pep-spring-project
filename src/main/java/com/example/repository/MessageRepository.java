package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Transactional
    @Modifying
    @Query("delete from Message m where m.messageId = :messageId")
    int deleteByMessageId(@PathVariable Integer messageId);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int updateMessageText(@Param("messageId") Integer messageId, @Param("messageText") String messageText);

    List<Message> findByPostedBy(Integer postedBy);
}
