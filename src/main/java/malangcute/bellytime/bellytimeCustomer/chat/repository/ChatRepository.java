package malangcute.bellytime.bellytimeCustomer.chat.repository;

import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatListResponseIF;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(nativeQuery = true,
            value = " select ct.room_id AS roomId, ct.room_name AS roomName ,ct.invite_id AS contactId, "
                  + "(select u.profile_img from users u where u.id = ct.invite_id) AS profileImg, "
                  + "(select u.nickname from users u where u.id = ct.invite_id) AS nickName "
                  + " from chat ct where ct.room_id  in ( "
                  + " select chat.room_id from chat where invite_id =:userId and type =:types) ")
    List<ChatListResponseIF> findMyChatList(@Param("userId")Long userId, @Param("types")String type);




    @Query(nativeQuery = true,
            value = " SELECT *, count(room_id) AS counts FROM chat cc WHERE cc.room_id  IN "
                + " (SELECT c.room_id FROM chat c WHERE c.invite_id=:inviteId) GROUP BY (room_id) "
                + " HAVING invite_id=:inviteId AND counts=2 AND type=:types ")
    Chat findSingleRoomIdExist(@Param("inviteId")Long inviteId, @Param("types") String types);



    @Modifying
    @Query("DELETE FROM Chat ch WHERE ch.roomId=:roomId AND ch.inviteId=:inviteId")
    void deleteByRoomIdAndInviteId(@Param("roomId")String roomId, @Param("inviteId") User inviteId);


    @Query("SELECT DISTINCT c FROM Chat c WHERE c.roomId=:roomId GROUP BY c.roomId")
    Chat findByRoomId(@Param("roomId")String roomId);
}


