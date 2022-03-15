package malangcute.bellytime.bellytimeCustomer.chat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {



    List<ChatLog> findBySenderAndRoomId(Long id, String request);

    @Query("SELECT cl FROM ChatLog cl WHERE cl.roomId=:roomId ORDER BY cl.createdAt DESC")
    Page<ChatLog> findByRoomId(@Param("roomId") String roomId, Pageable pageable);
}
