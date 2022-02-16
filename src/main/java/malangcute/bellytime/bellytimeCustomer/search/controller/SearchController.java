package malangcute.bellytime.bellytimeCustomer.search.controller;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchFoodRequest;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchDto;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchShopRequest;
import malangcute.bellytime.bellytimeCustomer.search.service.SearchService;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSaveRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
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


    //음식과 샵을 동시에 조회 및 반환
    @GetMapping("name/{name}")
    public ResponseEntity<?> searchAny(@PathVariable String name) {
        List<String> returnList = searchService.searching(name);
        return ResponseEntity.ok(returnList);
    }


    //score, follow순으로 반환
    @PostMapping("/resultlist")
    public ResponseEntity<?> searchShop(@RequestBody SearchShopRequest request) {
        List<ShopSearchResultListDto> list  =  searchService.specificSearch(request);
        return ResponseEntity.ok(list);
    }







        //예시코드

    @GetMapping("/shop/{name}")
    public ResponseEntity<?> searchShop1(@PathVariable String name) {
        List<String> shopList = shopService.searchByName(name);
        return ResponseEntity.ok(shopList);
    }

    @GetMapping("/food")
    public ResponseEntity<?> searchfood(@RequestBody SearchDto searchDto) {
        System.out.println(searchDto.getSearch());
        List<String> shopList = foodService.searchByName(searchDto.getSearch());
        return ResponseEntity.ok(shopList);
    }

    //새로운 가게 저장
    @PostMapping("/shop")
    public ResponseEntity<?> saveShop(@RequestBody ShopSaveRequest shopSaveRequest) {
        shopService.saveShop(shopSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다");
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



