package malangcute.bellytime.bellytimeCustomer.user.controller;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.follow.dto.*;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.exception.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.NickName;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final FollowService followService;

    private final UserRepository userRepository;

    private final CoolTimeService coolTimeService;

    // 유저 정보 업데이트
    @GetMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute UserUpdateRequest userUpdateRequest) throws FailedToConvertImgFileException {
        userService.userUpdate(userUpdateRequest);
        return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity<?> toFollowShop(@RequireLogin User user, @RequestBody List<FollowShopRequest> requests) {
        followService.toFollowShop(user, requests);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // 유저가 팔로우 취소한 가게(팔로우 테이블에서 지우기)
    @DeleteMapping("/follow/shop")
    public ResponseEntity<?> toUnFollowShop(@RequireLogin User user, @RequestBody List<FollowShopRequest> requests) {
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
    public ResponseEntity<?> followMyFriends(@RequireLogin User user, @RequestBody List<FollowFriendsRequest> request) {
        followService.toFollowFriend(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    //팔로우 지우기
    @DeleteMapping("/follow/friends")
    public ResponseEntity<?> unFollowMyFriend(@RequireLogin User user, @RequestBody List<FollowFriendsRequest> request) {
        followService.toUnFollowFriend(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /*
     * cooltime cal 서비스
     *
     */


    //달력 요청시 갖고오기 -> year, month 받아 요청마다 데이터 조회 반환
    @PostMapping("/cal")
    public ResponseEntity<?> myCoolTimeCalender(@RequireLogin User user, @RequestBody CoolTimeCalRequest request) {
        CoolTimeCalListResponse1 list = coolTimeService.getMyCoolTimeCal(user, request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
