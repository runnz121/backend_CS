package malangcute.bellytime.bellytimeCustomer.food.repository.elastic;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CustomFoodSearchRepositoryImpl implements CustomFoodSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Food> searchByName(String name) {

        //엘라스틱 서치 크리테리아 임포트
        Criteria criteria = Criteria.where("name").contains(name);
        Query query = new CriteriaQuery(criteria);
        //SearchHits<Shop> searchShop = elasticsearchOperations.search(query, Shop.class);
        SearchHits<Food> searchFood = elasticsearchOperations.search(query, Food.class);

        return searchFood.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
//
//    @Override
//    public List<Shop> searchByName(String name, Pageable pageable) {
//
//        //엘라스틱 서치 크리테리아 임포트
//        Criteria criteria = Criteria.where("name").contains(name);
//        Query query = new CriteriaQuery(criteria).setPageable(pageable);
//        SearchHits<Shop> search = elasticsearchOperations.search(query, Shop.class);
//
//        return search.stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//    }
}
