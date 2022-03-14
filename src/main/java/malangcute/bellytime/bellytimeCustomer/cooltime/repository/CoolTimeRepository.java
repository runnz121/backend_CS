package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeGetMyFriendsIF;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeList;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeListIF;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoolTimeRepository extends JpaRepository<CoolTime, Long> {

    @Query("SELECT f.id AS foodId, f.name AS foodName, "
        + "f.image AS foodImg, c.gauge AS gauge, c.startDate AS startDate, "
        + "c.endDate AS endDate, c.duration AS duration, c.eat AS eat "
        + "FROM CoolTime c LEFT JOIN FETCH Food f ON c.foodId.id= f.id WHERE c.userId.id=:userId ")
    List<GetMyCoolTimeListIF> findMyCoolTime(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM CoolTime c WHERE c.userId.id=:userId AND c.foodId.id=:foodId ")
    void deleteByUserId(@Param("userId") Long userId, @Param("foodId") Long foodId);

    @Query("SELECT c.id FROM CoolTime c WHERE c.userId.id=:userId AND c.foodId.id=:foodId")
    Optional<CoolTime> findUserIdAndFoodId(@Param("userId")Long userId, @Param("foodId")Long foodId);

    @Modifying
    @Query("UPDATE CoolTime c "
        + "SET c.startDate=:startDate, c.endDate=:endDate,c.gauge=:gauge, "
        + "c.duration=:duration WHERE c.userId.id=:userId and c.foodId.id=:foodId")
    void updateByUserId(@Param("userId") Long userId, @Param("foodId")Long foodId,
                        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                        @Param("gauge") String gauge, @Param("duration") Integer duration);



    @Modifying
    @Query("UPDATE CoolTime c SET c.eat=:status WHERE c.foodId.id=:foodId AND c.userId.id=:userId ")
    void updateEatByUserId(@Param("status") Boolean status, @Param("userId") Long userId, @Param("foodId") Long FoodId);


    //배치용
    List<CoolTime> findByEat(boolean b);


    // 친구 쿨타임 갖고오기(쿨타임 추천페이지)
    @Query(nativeQuery = true, value =
            " select" +
            " DISTINCT " +
            " u.nickname AS name," +
            " c.gauge AS gauge," +
            " u.profile_img AS profileImg," +
            " u.id As friendId" +
            " from cooltime c" +
            " left join users u " +
            " on c.user_id = u.id " +
            " left join follow_user fu " +
            " on fu.friend_id = u.id " +
            " left join food f " +
            " on c.food_id = f.id " +
            " where fu.host_id=:userId and f.id=:foodId" +
            " ")
    List<CoolTimeGetMyFriendsIF> findMyCoolTimeFriends(@Param("userId") Long hostId, @Param("foodId") Long foodId);


}
