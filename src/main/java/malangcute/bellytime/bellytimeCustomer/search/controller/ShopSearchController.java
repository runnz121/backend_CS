package malangcute.bellytime.bellytimeCustomer.search.controller;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSaveRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResponse;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/searchby")
@RestController
public class ShopSearchController {

    private final ShopService shopService;

//    Type 1 => http://127.0.0.1?index=1&page=2 -> @RequestParam
//    Type 2 => http://127.0.0.1/index/1 -> @PathVariable


    //식당 이름으로 음식 검색
    @GetMapping("/shop/{name}")
    public ResponseEntity<?> searchShop(@PathVariable String name, Pageable pageable) {
        List<ShopSearchResponse> shopList = shopService.searchByName(name, pageable)
                .stream()
                .map(ShopSearchResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shopList);
    }


    //새로운 가게 저장
    @PostMapping("/shop")
    public ResponseEntity<?> saveShop(@RequestBody ShopSaveRequest shopSaveRequest) {
        shopService.saveShop(shopSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다");
    }
}
