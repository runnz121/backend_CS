package malangcute.bellytime.bellytimeCustomer.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {



    List<ChatLog> findBySenderAndRoomId(Long id, String request);

    List<ChatLog> findByRoomId(String roomId);
}
