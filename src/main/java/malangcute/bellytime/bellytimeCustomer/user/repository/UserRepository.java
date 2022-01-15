package malangcute.bellytime.bellytimeCustomer.user.repository;

import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);
}
