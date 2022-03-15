package malangcute.bellytime.bellytimeCustomer.cooltime.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDateList;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFriendRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeGetMyFriends;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeSettingRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeShopRecommendResponse;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.DeleteCoolTimeRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeList;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopRepository;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@RestController
@RequestMapping("/cooltime")
@AllArgsConstructor
public class CoolTimeController {

    private final CoolTimeService coolTimeService;

    private final UserService userService;

    //마이리스트 불러오기
    @GetMapping("/mylist")
    public ResponseEntity<List<GetMyCoolTimeList> > myList(@RequireLogin User user) {
        List<GetMyCoolTimeList> myList = coolTimeService.getMyList(user);
        return ResponseEntity.status(HttpStatus.OK).body(myList);
    }

    //쿨타임 업데이트 및 저장하기
    @PostMapping("/setting")
    public ResponseEntity<?> setting(@RequireLogin User user, @RequestBody CoolTimeSettingRequest request)
        throws ParseException {
        coolTimeService.settingCoolTime(user, request);
        return new ResponseEntity<>("요청완료", HttpStatus.OK);
    }

    //쿨타임 삭제하기
    @DeleteMapping("/setting")
    public ResponseEntity<?> delete(@RequireLogin User user, @RequestBody DeleteCoolTimeRequest request) {
        coolTimeService.deleteCoolTime(user, request);
        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }


    @PostMapping("/cal")
    public ResponseEntity<CoolTimeCalListResponse1> getMyFriendCoolTime(@RequireLogin User user,
        @RequestBody CoolTimeCalFriendRequest request) {
        User friend = userService.findUserById(request.getFriendId());

        CoolTimeCalListResponse1 list = coolTimeService.selected(friend, request.getMonth(), request.getYear(), "friend");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @GetMapping("/followList")
    public ResponseEntity<List<CoolTimeGetMyFriends>> myFriendsCoolTime(@RequireLogin User user, @RequestParam Long foodId) {
        List<CoolTimeGetMyFriends> lists = coolTimeService.getMyFriendsListByFood(user, foodId);
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/shopList")
    public ResponseEntity<List<CoolTimeShopRecommendResponse>> getCoolTimeByFilter(@RequireLogin User user,
                                                                                   @RequestParam Long foodId,
                                                                                   @RequestParam String filter,
                                                                                   @RequestParam(required = false)
                                                                                       Double lat,
                                                                                   @RequestParam(required = false)
                                                                                       Double lon,
                                                                                   @PageableDefault(page = 1) Pageable page) {

        List<CoolTimeShopRecommendResponse> list = coolTimeService.getShopListFilterBy(user, foodId, filter, lat, lon, page);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
