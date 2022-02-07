package malangcute.bellytime.bellytimeCustomer.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ResultListDto;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSaveRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResponse;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopRepository;
import malangcute.bellytime.bellytimeCustomer.shop.repository.elastic.ShopSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ShopService {

    //jpa
    private final ShopRepository shopRepository;

    //elastic
    private final ShopSearchRepository shopSearchRepository;

    @Transactional
    public void saveShop(ShopSaveRequest shopSaveRequest) {
        String name = shopSaveRequest.getName();
        String address =shopSaveRequest.getAddress();
        String img = shopSaveRequest.getImg();
        Shop newshop =  Shop.builder()
                .name(name)
                .address(address)
                .image(img)
                .build();
        shopRepository.save(newshop);
        shopSearchRepository.save(newshop);
    }


    // Shop형태로 결과값을 받아 shopsearchreusltlistdto로 맵핑한다
    public List<ResultListDto> searchByName(String name) {

        return shopSearchRepository.searchByName(name)
                .stream()
                .map(ResultListDto::of)
                .collect(Collectors.toList());
    }

    // Shop형태로 결과값을 받아 shopsearchreusltlistdto로 맵핑한다
//    public List<ShopSearchResultListDto> searchByName(String name, Pageable pageable) {
//
//
//        return shopSearchRepository.searchByName(name, pageable)
//                .stream()
//                .map(ShopSearchResultListDto::from)
//                .collect(Collectors.toList());
//    }
}
