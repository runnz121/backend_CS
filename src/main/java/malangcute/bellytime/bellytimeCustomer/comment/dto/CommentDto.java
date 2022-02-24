package malangcute.bellytime.bellytimeCustomer.comment.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.domain.Comment;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long shopId;

    private String shopName;

    private String state;

    private Long score;


    public static CommentDto of (CommentDtoIF comment) {
        return new CommentDto(
                comment.getShopId(),
                comment.getShopName(),
                comment.getState(),
                comment.getScore());
    }
}
