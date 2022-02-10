package malangcute.bellytime.bellytimeCustomer.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.search.dto.AssembleListDto;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchShopRequest;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.*;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopRepository;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopSortStrategyFactory;
import malangcute.bellytime.bellytimeCustomer.shop.repository.elastic.ShopSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
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


    private final ShopSortStrategyFactory shopSortStrategyFactory;

    //음식점 저장
    @Transactional
    public void saveShop(ShopSaveRequest shopSaveRequest) {
        String name = shopSaveRequest.getName();
        String address = shopSaveRequest.getAddress();
        String img = shopSaveRequest.getImg();
        Shop newshop = Shop.builder()
                .name(name)
                .address(address)
                .image(img)
                .build();
        shopRepository.save(newshop);
        shopSearchRepository.save(newshop);
    }


    // Shop형태로 결과값을 받아 shopsearchreusltlistdto로 맵핑한다
    public List<String> searchByName(String name) {
        List<String> getList = shopRepository.findByNameContaining(name)
                .stream().map(it -> it.getName()).collect(Collectors.toList());
        return getList;
    }

    // 전략 패턴 적용
    public List<ShopSearchResultListDto> searchBySpecificName(String name, String sortType) {
        return shopSortStrategyFactory.findStrategy(sortType).SortedList(name);
    }





//    //기본형
//    public List<ShopSearchResultListDto> searchBySpecificName(String name) {
//        //List<ShopDetailDto> list = shopRepository.findNameDetail(name);
//
//        List<Shop> list = shopRepository.findAllByNameContaining(name);
//        System.out.println("in list " + list);
//        List<ShopSearchResultListDto> all = list.stream()
//                .map(ShopSearchResultListDto::of)
//                .collect(Collectors.toList());
//        return all;
//    }
//
//
//    //팔로워순
//    public List<ShopSearchResultListDto> searchBySpecificName1(String name) {
//        //List<ShopDetailDto> list = shopRepository.findNameDetail(name);
//
//        List<Shop> list = shopRepository.findAllByNameContaining(name);
//        System.out.println("in list " + list);
//        List<ShopSearchResultListDto> all = list.stream()
//                .sorted(Comparator.comparing(Shop::getFollower).reversed())
//                .map(ShopSearchResultListDto::of)
//                .collect(Collectors.toList());
//        return all;
//    }
//
//
//    //벨스코어순
//    public List<ShopSearchResultListDto> searchBySpecificName2(String name) {
//        //List<ShopDetailDto> list = shopRepository.findNameDetail(name);
//
//        List<Shop> list = shopRepository.findAllByNameContaining(name);
//        System.out.println("in list " + list);
//        List<ShopSearchResultListDto> all = list.stream()
//                .sorted(Comparator.comparing(Shop::getBellscore).reversed())
//                .map(ShopSearchResultListDto::of)
//                .collect(Collectors.toList());
//        return all;
//    }





}













    //pk리스트 조회
//    public List<ShopSearchResultListDto> searchBySpecificName(String name) {
//
//        List<ShopSearchResultListDto> list =  shopRepository.findNameDetail(name)
//                .stream()
//                .map(ShopSearchResultListDto::from)
//                .collect(Collectors.toList());
//
//        System.out.println("in shopService " + list);
//        return list;
//    }

//    public List<MenuListDto> searchMenuBySpecificName(ShopSearchResultListDto shopList) {
//        List<MenuListDto> list = shopRepository.findMenus(shopList.getShop_id())
//                .stream()
//                .filter(it -> it.getId())
//
//    }

