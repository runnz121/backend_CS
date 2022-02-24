package malangcute.bellytime.bellytimeCustomer.follow.repository;

import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowUser;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendSearchResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;

public interface FollowUserRepository extends JpaRepository<FollowUser,Long> {

    // 기가 팔로우하고 있는 유저목록 리스트 불러오기
    @Query("SELECT distinct fs FROM FollowUser fs JOIN FETCH fs.hostId uid WHERE uid.id=:userId ")
    List<FollowUser> findMyFollowList(@Param("userId") Long userId);


    //유저가 선택한 팔로워 삭제
    @Modifying
    @Query("DELETE FROM FollowUser fu WHERE fu.hostId.id=:userId AND fu.friendId.id=:shopId ")
    void deleteFollowShopId(@Param("userId") Long id, @Param("shopId") Long shopId);

}
