package malangcute.bellytime.bellytimeCustomer.comment.repository;

import malangcute.bellytime.bellytimeCustomer.comment.domain.Comment;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentDtoIF;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT r.shopId.id AS shopId, r.shopId.name AS shopName, c.state AS state, c.score AS score " +
            "From Comment c JOIN Reservation r ON c.reservationId.id = r.id WHERE c.userId.id=:userId ")
    List<CommentDtoIF> findCommentsById(@Param("userId") Long userId);


    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.shopId.id=:shopId" )
    int getReviewCount(@Param("shopId") Long shopId);
}
