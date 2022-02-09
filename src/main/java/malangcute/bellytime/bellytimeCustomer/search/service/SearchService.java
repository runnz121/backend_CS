package malangcute.bellytime.bellytimeCustomer.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.food.repository.elastic.CustomFoodSearchRepository;
import malangcute.bellytime.bellytimeCustomer.food.repository.elastic.FoodSearchRepository;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchShopRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResponse;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;
import malangcute.bellytime.bellytimeCustomer.shop.repository.elastic.CustomShopSearchRepository;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final ShopService shopService;

    private final FoodService foodService;


    //food, shop 전체조회
    @Transactional(readOnly = true)
    public List<String> searching(String name) {
        List<String> food = foodService.searchByName(name);
        List<String> shop = shopService.searchByName(name);

        System.out.println("inservice  "+food);
        System.out.println(shop);
        return listAssemble(food, shop);
    }

    //리스트 합치기
    private List<String> listAssemble(List<String> A, List<String> B) {
        return Stream.of(A, B)
                .flatMap(Collection::stream)//x -> x.stream()
                .collect(Collectors.toList());
    }


    public List<ShopSearchResultListDto> specificSearch(SearchShopRequest request) {
        System.out.println("searchservice name " + request.getName());
        return shopService.searchBySpecificName(request.getName());
    }

}
