package malangcute.bellytime.bellytimeCustomer.food.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchFoodRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchResultResponse;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.repository.FoodRepository;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoFoodException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
@ToString
public class FoodService {

    private final FoodRepository foodRepository;

    //음식 검색하기
    public List<SearchResultResponse> findFood(SearchFoodRequest request) {
        String findFood = request.getSearch();
        List<SearchResultResponse> resultList = foodRepository.findByName(findFood)
                .stream().map(item -> new SearchResultResponse(item.getId(), item.getName()))
                .collect(Collectors.toList());
        return resultList;
    }

    //food name으로 food 반환
    public Food findFoodFromName(String foodName) {
        Optional<Food> findFood = foodRepository.findByName(foodName);
        Food food = findFood.orElseThrow(() -> new NoFoodException("해당음식이 없습니다"));
        return food;
    }

}
