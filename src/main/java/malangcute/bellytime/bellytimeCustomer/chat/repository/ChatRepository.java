package malangcute.bellytime.bellytimeCustomer.chat.repository;

import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    //List<Chat> findByMakerIdOrInviteIdAndType(User inMakerId, User inInviteId, String type);

    List<Chat> findByMakerIdAndType(User MakerId, String type);

    Chat findByMakerId(User makerId);


    @Query(nativeQuery = true,
            value = " SELECT room_id FROM ( " +
                    " SELECT COUNT(c1.room_id) cnt, c1.room_id FROM chat AS c1 WHERE c1.room_id in " +
                    "(SELECT c2.room_id from chat AS c2 WHERE c2.maker_id =:makerId AND c2.invite_id=:inviteId AND c2.type=:types) " +
                    " GROUP BY c1.room_id) c3 WHERE c3.cnt = 1")
    String findSingleRoomIdExist(@Param("makerId")Long makerId, @Param("inviteId")Long inviteId, @Param("types") String types);



    @Modifying
    void deleteByRoomId(String roomId);


}


