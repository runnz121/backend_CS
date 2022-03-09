package malangcute.bellytime.bellytimeCustomer.user.controller;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentDto;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentWriteRequest;
import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCheckRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.follow.dto.*;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.reservation.dto.ReservationShopInfoResponse;
import malangcute.bellytime.bellytimeCustomer.reservation.service.ReservationService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final FollowService followService;

    private final UserRepository userRepository;

    private final CoolTimeService coolTimeService;

    private final ReservationService reservationService;

    private final CommentService commentService;

    // 유저 정보 업데이트
    @GetMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute UserUpdateRequest userUpdateRequest)
            throws FailedToConvertImgFileException {
        userService.userUpdate(userUpdateRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //유저 프로필 이미지 이름 갖고오기
    @GetMapping("/myprofile")
    public ResponseEntity<?> getMyProfile(@RequireLogin  User user) {
        UserProfileResponse result = userService.userProfile(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * Follow service
     */

    //유저가 팔로우한 가게 갖고오기
    @GetMapping("/follow/shop")
    public ResponseEntity<?> myFollowShopList(@RequireLogin User user) {
        List<MyFollowShopResponse> list = followService.myFollowShop(user);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //유저가 팔로우할 가게 저장하기 여러개
    @PostMapping("/follow/shop")
    public ResponseEntity<?> toFollowShop(@RequireLogin User user,
                                          @RequestBody List<FollowShopRequest> requests) {
        followService.toFollowShop(user, requests);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // 유저가 팔로우 취소한 가게(팔로우 테이블에서 지우기)
    @DeleteMapping("/follow/shop")
    public ResponseEntity<?> toUnFollowShop(@RequireLogin User user,
                                            @RequestBody List<FollowShopRequest> requests) {
        followService.toUnFollowShop(user, requests);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    // 팔로우 리스트 불러오기
   @GetMapping("/friends/list")
    public ResponseEntity<?> myFriendList(@RequireLogin User user) {
        List<MyFriendListResponse> list = followService.getMyFriends(user);
        return ResponseEntity.status(HttpStatus.OK).body(list);
   }

   //닉네임으로 단일건 서치
   @PostMapping("/friends/search")
    public ResponseEntity<?> findMyFriend(@RequestBody FindMyFriendSearchRequest request) {
        MyFriendSearchResponse result = userService.findUserByNickname(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
   }

   //친구 팔로우, 언팔로우하기
    @PostMapping("/follow/friends")
    public ResponseEntity<?> followMyFriends(@RequireLogin User user,
                                             @RequestBody List<FollowFriendsRequest> request) {
        followService.toFollowFriend(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    //팔로우 지우기
    @DeleteMapping("/follow/friends")
    public ResponseEntity<?> unFollowMyFriend(@RequireLogin User user,
                                              @RequestBody List<FollowFriendsRequest> request) {
        followService.toUnFollowFriend(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /*
     * cooltime cal 서비스
     *
     */

    //달력 요청시 갖고오기 -> year, month 받아 요청마다 데이터 조회 반환
    @PostMapping("/cal")
    public ResponseEntity<CoolTimeCalListResponse1> myCoolTimeCalender(@RequireLogin User user,
                                                @RequestBody CoolTimeCalRequest request) {
        CoolTimeCalListResponse1 list = coolTimeService.getMyCoolTimeCal(user, request.getMonth(), request.getYear());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    //쿨타임 먹은 음식 체크
    @PostMapping("/cal/check")
    public ResponseEntity checkMyCoolTime(@RequireLogin User user,
                                             @RequestBody List<CoolTimeCheckRequest> requests) {
        coolTimeService.updateCoolTimeEatCheck(user, requests);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /*
     * reservaiton 서비스
     *
     */
    @GetMapping("/reservation/list")
    public ResponseEntity<List<ReservationShopInfoResponse>> myReservationList(@RequireLogin User user) {
        List<ReservationShopInfoResponse> list = reservationService.myReservationList(user);
        return ResponseEntity.ok(list);
    }

    //애약 상태 반환 -> 수정 필요(상의)
//    public ResponseEntity<ReservationShopInfoResponse> myReservationState(@RequireLogin User user,
//                                                @RequestBody ReservationStateDto) {
//        return reservationService.
//    }


    /**
     * comment 서비스
     */

    //내가 작성한 후기 모두 갖고오기
    @GetMapping("/comment/list")
    public ResponseEntity<List<CommentDto>> getMyCommentList(@RequireLogin User user) {
        List<CommentDto> list = commentService.getMyComment(user);
        return ResponseEntity.ok(list);
    }

    //후기 작성하기
    @PostMapping("/comment/write")
    public ResponseEntity writeComment(@RequireLogin User user,
                                       @ModelAttribute CommentWriteRequest request) {
        commentService.updateMyComment(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


}
