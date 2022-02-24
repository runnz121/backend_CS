package malangcute.bellytime.bellytimeCustomer.food.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchFoodRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchResultResponse;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;

    //음식 검색하기
    @PostMapping("/searchfood")
    public ResponseEntity<?> search(@RequestBody SearchFoodRequest searchFoodRequest){
        List<SearchResultResponse> searchResults = foodService.findFood(searchFoodRequest);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

    @PostMapping("/savefood")
    public void saveFood(@RequestBody SearchFoodRequest searchFoodRequest) {
        foodService.registerFood(searchFoodRequest.getSearch());
    }
}
