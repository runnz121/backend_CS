package malangcute.bellytime.bellytimeCustomer.reservation.repository;

import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(User user);
}
