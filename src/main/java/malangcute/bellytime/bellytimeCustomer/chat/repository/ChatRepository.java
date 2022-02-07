package malangcute.bellytime.bellytimeCustomer.chat.repository;

import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    //AS 로 인터페이스 필드네임과 매칭시켜주어야 한다
    @Query("SELECT c.roomName AS roomName , c.groupId AS groupId FROM Chat c WHERE c.roomOwner.id=:owner AND c.consumer.id=:consumer ")
    Optional<GetRoomAndGroupFromRepo> findChatRoom(@Param("owner") Long owner, @Param("consumer") Long consumer);

}
