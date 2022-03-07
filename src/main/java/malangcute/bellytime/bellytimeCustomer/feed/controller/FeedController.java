package malangcute.bellytime.bellytimeCustomer.feed.controller;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.feed.repository.FeedSortStrategyFactory;
import malangcute.bellytime.bellytimeCustomer.feed.service.FeedService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;



    // 내가 구독한 가게 피드 갖고오기
//    @GetMapping("/list")
//    public ResponseEntity<List<FeedListResponse>> getMyFeedListBySub (@RequireLogin User user,
//                                                                      @RequestParam String filter,
//                                                                      @PageableDefault Pageable pageable) {
//        List<FeedListResponse> lists = feedService.getListBySub(user, filter, pageable);
//
//        return ResponseEntity.status(HttpStatus.OK).body(lists);
//    }

//    // 위도경도 받아서 근처 있는 가게 피드갖고오기
    @GetMapping("/list")
    public ResponseEntity<List<FeedListResponse>> getMyFeedListByNearBy (@RequireLogin User user,
                                                                         @RequestParam String filter,
                                                                         @RequestParam(required = false) Long lat,
                                                                         @RequestParam(required = false) Long lon,
                                                                         @PageableDefault Pageable pageable) {
        List<FeedListResponse> lists = feedService.getListBy(user, filter, lat, lon, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }
}
