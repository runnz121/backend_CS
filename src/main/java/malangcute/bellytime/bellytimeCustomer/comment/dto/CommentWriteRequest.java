package malangcute.bellytime.bellytimeCustomer.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CommentWriteRequest {

    private Long score;

    private String state;

    private String visible;

    private String Content;

    private Long reservationId;

    private List<MultipartFile> images;
}
