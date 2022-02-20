package malangcute.bellytime.bellytimeCustomer.cooltime.controller;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.*;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cooltime")
@AllArgsConstructor
public class CoolTimeController {

    private final CoolTimeService coolTimeService;

    private final UserService userService;


    //마이리스트 불러오기
    @GetMapping("/mylist")
    public ResponseEntity<?> myList(@RequireLogin User user){
        List<GetMyCoolTimeList> myList = coolTimeService.getMyList(user);
        return ResponseEntity.status(HttpStatus.OK).body(myList);
    }

    //쿨타임 업데이트 및 저장하기
    @PostMapping("/setting")
    public ResponseEntity<?> setting(@RequireLogin User user, @RequestBody CoolTimeSettingRequest request) throws ParseException {
        coolTimeService.settingCoolTime(user, request);
        return new ResponseEntity<>("요청완료", HttpStatus.OK);
    }


    //쿨타임 삭제하기
    @DeleteMapping("/setting")
    public ResponseEntity<?> delete(@RequireLogin User user, @RequestBody DeleteCoolTimeRequest request){
        coolTimeService.deleteCoolTime(user, request);
        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }

    //친구 쿨타입 갖고오기
    @PostMapping("/cal")
    public ResponseEntity<?> getMyFriendCoolTime(@RequestBody CoolTimeCalFriendRequest request) {
        User friend = userService.findUserById(request.getFriendId());
        CoolTimeCalListResponse1 list = coolTimeService.getMyCoolTimeCal(friend, request.getMonth(), request.getYear());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    //health check
    @GetMapping("/check")
    public String check(@RequireLogin User user) {
        System.out.println("check"+user.getId());
        return "check cooltime";
    }
}
