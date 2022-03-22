package malangcute.bellytime.bellytimeCustomer.search.controller;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.search.dto.*;
import malangcute.bellytime.bellytimeCustomer.search.service.SearchService;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSaveRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListResponse;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/searchby")
@RestController
public class SearchController {

    private final ShopService shopService;

    private final FoodService foodService;

    private final SearchService searchService;

//    Type 1 => http://127.0.0.1?index=1&page=2 -> @RequestParam
//    Type 2 => http://127.0.0.1/index/1 -> @PathVariable


    // 음식과 샵을 동시에 조회 및 반환
    @GetMapping("/name/{name}")
    public ResponseEntity<?> searchAny(@RequireLogin User user, @PathVariable String name) {
       // List<String> returnList = searchService.searching(user, name);
        SearchResultList returnList = searchService.searching(user, name);
        return ResponseEntity.ok(returnList);
    }


    // score, follow순으로 반환
    @PostMapping("/resultlist")
    public ResponseEntity<List<ShopSearchResultListResponse>> searchShop(@RequireLogin User user, @RequestBody SearchShopRequest request) {
        List<ShopSearchResultListResponse> list  =  searchService.specificSearch(user, request);
        return ResponseEntity.ok(list);
    }

    // 가게 이름으로 찾기
    @GetMapping("/shop/{name}")
    public ResponseEntity<List<String>> searchShop1(@PathVariable String name) {
        List<String> shopList = shopService.searchByName(name);
        return ResponseEntity.ok(shopList);
    }

    // 음식 찾기
    @GetMapping("/food")
    public ResponseEntity<List<String>> searchfood(@RequestBody SearchDto searchDto) {
        List<String> shopList = foodService.searchByName(searchDto.getSearch());
        return ResponseEntity.ok(shopList);
    }

    //새로운 가게 저장
    @PostMapping("/shop")
    public ResponseEntity saveShop(@RequestBody ShopSaveRequest shopSaveRequest) {
        shopService.saveShop(shopSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다");
    }

    //최근 음식 검색어 반환
    @GetMapping("/recent")
    public ResponseEntity<SearchRecentListResponse> recentSearchList(@RequireLogin User user) {
        SearchRecentListResponse list = searchService.recentSearch(user);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/recent")
    public void deleteRecentSearch(@RequireLogin User user, @RequestBody SearchDeleteRecentListRequest request) {
        searchService.deleteRecentSearch(user, request);
    }
}


    //식당 이름으로 음식 검색
//    @GetMapping("/shop/{name}")
//    public ResponseEntity<?> searchShop(@PathVariable String name, Pageable pageable) {
//        List<ShopSearchResponse> shopList = shopService.searchByName(name, pageable)
//                .stream()
//                .map(ShopSearchResponse::from)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(shopList);
//    }
//
//    @PostMapping("/resultlist")
//    public ResponseEntity<?> searchList



