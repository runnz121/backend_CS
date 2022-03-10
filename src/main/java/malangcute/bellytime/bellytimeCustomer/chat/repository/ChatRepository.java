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

    //List<Chat> findByMakerIdOrInviteIdAndType(User inMakerId, User inInviteId, String type);

    List<Chat> findByMakerIdAndType(User MakerId, String type);

    Chat findByMakerId(User makerId);



    @Query(nativeQuery = true,
            value = " select ct.room_id AS roomId, ct.room_name AS roomName ,ct.invite_id AS contactId, " +
                    "(select u.profile_img from users u where u.id = ct.invite_id) AS profileImg, " +
                    "(select u.nickname from users u where u.id = ct.invite_id) AS nickName " +
                    " from chat ct where ct.room_id  in ( " +
                    " select chat.room_id from chat where invite_id =:userId and type =:type) ")
    List<ChatListResponseIF> findMyChatList(@Param("userId")Long userId, @Param("type")String type);



    @Query(nativeQuery = true,
            value = " SELECT room_id FROM ( " +
                    " SELECT COUNT(c1.room_id) cnt, c1.room_id FROM chat AS c1 WHERE c1.room_id in " +
                    "(SELECT c2.room_id from chat AS c2 WHERE c2.maker_id =:makerId AND c2.invite_id=:inviteId AND c2.type=:types) " +
                    " GROUP BY c1.room_id) c3 WHERE c3.cnt = 1")
    String findSingleRoomIdExist(@Param("makerId")Long makerId, @Param("inviteId")Long inviteId, @Param("types") String types);



    @Modifying
    @Query("DELETE FROM Chat ch WHERE ch.roomId=:roomId AND ch.inviteId=:inviteId")
    void deleteByRoomIdAndInviteId(@Param("roomId")String roomId, @Param("inviteId") User inviteId);


    @Query("SELECT DISTINCT c FROM Chat c WHERE c.roomId=:roomId GROUP BY c.roomId")
    Chat findByRoomId(@Param("roomId")String roomId);
}


