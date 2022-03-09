package malangcute.bellytime.bellytimeCustomer.food.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchFoodRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchResultResponse;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.dto.FoodResultDto;
import malangcute.bellytime.bellytimeCustomer.food.repository.FoodRepository;

import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NoFoodException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
@ToString
public class FoodService {

 //   private final FoodSearchRepository foodSearchRepository;

    private final FoodRepository foodRepository;

    private final UserService userService;

    //음식 검색하기 -> list반환
    public List<SearchResultResponse> findFood(SearchFoodRequest request) {
        String findFood = request.getSearch();
        List<FoodResultDto> dtoList = foodRepository.findByNameContaining(findFood);
        List<SearchResultResponse> resultList =
                dtoList.stream().map(item -> new SearchResultResponse(item.getId(), item.getName()))
                .collect(Collectors.toList());
        return resultList;
    }

    //food name으로 food 반환 -> 단일반환
    public Food findFoodFromName(String foodName) {
        try {
            Optional<Food> findFood = foodRepository.findByName(foodName);
            Food food = findFood.orElseThrow(() -> new NoFoodException("해당음식이 없습니다"));
            return food;
        } catch (NoFoodException ex) {
            Food food = registerFood(foodName);
            return food;
        }
    }

    public Food registerFood(String foodName) {
        Food registerNew = Food.builder()
                .name(foodName)
                .build();
       // foodSearchRepository.save(registerNew);
        foodRepository.save(registerNew);
        return registerNew;
    }


    //엘라스틱 서치 사용 -> mysql 사용
    public List<String> searchByName(String name) {
        //List<String> deList = Collections.synchronizedList(new ArrayList<>(Arrays.asList()));
        List<String> getList = foodRepository.findByNameContaining(name).stream().map(it -> it.getName()).collect(Collectors.toList());

        //System.out.println(deList);
        System.out.println(getList);

        //return  getList.parallelStream().collect(Collectors.toCollection(() -> deList));
        return getList;
    }



    //유저로 쿨타임 필터링 후 음식 찾아 반환하기 (리포지토리 확인)
    public List<Food> findFoodWithUserIdInCoolTime(User user) {
        return foodRepository.findByFoodWithUser(user.getId());
    }
}
