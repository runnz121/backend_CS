package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.domain.ShopMenu;

import java.awt.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class ShopSearchResultListDto {

    private Long shop_id;

    private String shop_name;

    private String profile_img;

    private Long score;

    private String address;

    private String runtime;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private int followCount;

   // private List<MenuListDto> menu; -> 상세장보 반환

    private List<String> menu;

    //private List<MenuListDto> menu; -> 인터페이스 렙퍼용



    public static ShopSearchResultListDto of (Shop shop) {

        // 메뉴 상세 정보 반환
//        List<MenuListDto> menus = shop.getShopId()
//                .stream()
//                .map(MenuListDto::of)
//                .collect(Collectors.toList());


        // 메뉴 스트링 리스트로 반환
        List<String> menus = shop.getShopId()
                .stream()
                .map(ShopMenu::getMenu)
                .collect(Collectors.toList());

        return new ShopSearchResultListDto(shop.getId(), shop.getName(), shop.getImage(), shop.getBellscore(),
        shop.getAddress(), shop.getRuntime(), shop.getLongitude(),  shop.getLatitude(), shop.getFollower(), menus);
    }



//    public ShopSearchResultListDto(Shop shop) {
//        shop_id = shop.getId();
//        shop_name = getShop_name();
//        profile_img = shop.getImage();
//        score = shop.getBellscore();
//        address = shop.getAddress();
//        runtime = shop.getRuntime();
//        longitude = shop.getLongitude();
//        latitude = shop.getLatitude();
//        followCount = shop.getBellscore();
//        //menu = shop.getShopId();
//        menu = shop.getShopId().stream()
//                .map(MenuListDto::new)
//                .collect(Collectors.toList());
//  }
}



// -> 이건 보류 1안

//    public static ShopSearchResultListDto from (ShopDetailDto shop) {
//        return new ShopSearchResultListDto(shop.getId(), shop.getName(), shop.getImg(), shop.getBellscore(),
//                shop.getAddress(), shop.getRuntime(), shop.getLongitude(), shop.getLatitude(), shop.getFollowerCount(),
//                ShopSearchResultListDto.of(shop.getMenu()));
//    }
//
//    public static List<MenuListDto> of (List<MenusList> shop) {
//        return shop.stream()
//                .map(it -> MenuListDto.of(it.getId(), it.getMenu()))
//                .collect(Collectors.toList());
//    }



// 2안

//    public static ShopSearchResultListDto of (ShopSearchResultListDto dto) {
//        return new ShopSearchResultListDto(dto.getShop_id(), dto.getShop_name(), dto.getProfile_img(), dto.getScore(), dto.getAddress(),
//                dto.getRuntime(), dto.getLongitude(), dto.getLatitude(), dto.getFollowCount(),dto.getMenu());
//    }



//        System.out.println("indto >><>" + list.getMenu());
//
//        List<MenuListDto> menus = list.getMenu()
//                .stream()
//                .map(MenuListDto::from)
//                .collect(Collectors.toList());
//        System.out.println("indto >><>" + menus.get(0)+"<>");

//        return new ShopSearchResultListDto(
//                list.getId(),
//                list.getName(),
//                list.getImg(),
//                list.getBellscore(),
//                list.getAddress(),
//                list.getRuntime(),
//                list.getLongitude(),
//                list.getLatitude(),
//                list.getFollowerCount(),
//                menus
//        );