package malangcute.bellytime.bellytimeCustomer.comment.service;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.domain.Comment;
import malangcute.bellytime.bellytimeCustomer.comment.domain.CommentImg;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentDto;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentWriteRequest;
import malangcute.bellytime.bellytimeCustomer.comment.repository.CommentImgRepository;
import malangcute.bellytime.bellytimeCustomer.comment.repository.CommentRepository;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.reservation.service.ReservationService;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final ReservationService reservationService;

    private final CommentImgRepository commentImgRepository;

    private final AwsS3uploader awsS3uploader;

    //유저아이디로 코멘드 반환
    public List<CommentDto> getMyComment(User user) {
        return commentRepository.findCommentsById(user.getId()).stream()
                .map(CommentDto::of)
                .collect(Collectors.toList());
    }

    //comment 작성
    public void updateMyComment(User user, CommentWriteRequest request) {
        Comment comment = Comment.builder()
                .reservation(reservationService.findByReservationId(request.getReservationId()))
                .request(request)
                .user(user)
                .build();
        commentRepository.save(comment);
        updateCommentImg(request, comment);
    }

    //comment 이미지 저장
    public void updateCommentImg(CommentWriteRequest request, Comment comment) {
        List<CommentImg> saveImgList = new ArrayList<>();
        for (MultipartFile img : request.getImages()) {
            saveImgList.add(CommentImg.builder()
                    .imgUrl(uploadImg(img))
                    .comment(comment)
                    .build());
        }
        commentImgRepository.saveAll(saveImgList);
    }

    //이미지 업로드
    private String uploadImg(MultipartFile img)  {
        try {
            return awsS3uploader.upload(img);
        } catch (FailedToConvertImgFileException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int reviewCountByShopId(Shop shop) {
        return commentRepository.getReviewCount(shop.getId());
    }
}
