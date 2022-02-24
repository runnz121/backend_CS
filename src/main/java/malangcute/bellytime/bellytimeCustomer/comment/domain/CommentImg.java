package malangcute.bellytime.bellytimeCustomer.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentImg extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment commentsId;


    @Builder
    public CommentImg(String imgUrl, Comment comment) {
        this.imgUrl = imgUrl;
        this.commentsId = comment;
    }

}
