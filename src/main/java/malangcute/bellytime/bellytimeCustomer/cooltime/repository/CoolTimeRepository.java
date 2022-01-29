package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoolTimeRepository extends JpaRepository<CoolTime, Long> {

   // List<CoolTime> findByuserId(String userId);

//    @Query(" SELECT f.id, f.name, f.image, c.gauge, c.startDate, c.endDate  FROM Food f, CoolTime c "
//    + " WHERE c.user =: user ")
//    List<Object[]> findMyCoolTime(@Param("user") Long user);

    //nativequery index가 1부터 시작함? 왜? 책에서는 0부터 시작한다고 했는데..
    @Query(value = "select  c.gauge from Food f, CoolTime c where c.user_id = ?1 ", nativeQuery = true)
    String findMyCoolTime(Long userId);

}
