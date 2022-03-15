package malangcute.bellytime.bellytimeCustomer.shop.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResponse;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/popular")
    public ResponseEntity<List<ShopSearchResponse>> getTop3PopularShopList(@RequireLogin User user) {
        List<ShopSearchResponse> list = shopService.getTop3ShopList(user);
        return ResponseEntity.ok(list);
    }

}
