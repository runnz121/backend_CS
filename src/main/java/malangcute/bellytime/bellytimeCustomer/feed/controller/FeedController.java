package malangcute.bellytime.bellytimeCustomer.feed.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedResultResponse;
import malangcute.bellytime.bellytimeCustomer.feed.service.FeedService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/list")
    public ResponseEntity<List<FeedListResponse>> getMyFeedListByFilter (@RequireLogin User user,
                                                                         @RequestParam String filter,
                                                                         @RequestParam(required = false) Double lat,
                                                                         @RequestParam(required = false) Double lon,
                                                                         @PageableDefault Pageable pageable) {
        List<FeedListResponse> lists = feedService.getListBy(user, filter, lat, lon, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/post")
    public ResponseEntity<FeedResultResponse> getFeedFromRequest (@RequireLogin User user,
                                                                  @RequestParam Long postId) {
        FeedResultResponse post = feedService.getFeedList(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

}
