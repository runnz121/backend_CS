package malangcute.bellytime.bellytimeCustomer.user.repository;

import malangcute.bellytime.bellytimeCustomer.follow.dto.FindMyFriendSearchRequest;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.NickName;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);

    Optional<User> findByNickname(NickName name);

    @Modifying
    @Query("UPDATE User u SET u.refreshToken=:status WHERE u.userId=:userId ")
    void logOutByUserId(@Param("userId") Long user, @Param("status") String status);
}
