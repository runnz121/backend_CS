package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.domain.ShopMenu;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.pl.REGON;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MenuListDto {

    private Long id;

    private String name;

    private Long price;

    private Food food;



    public static MenuListDto of (ShopMenu menu){
        return new MenuListDto(menu.getId(), menu.getMenu(), menu.getPrice(), menu.getFoodId());
    }




//    public MenuListDto(ShopMenu menu) {
//       // shop_id = menu.getShopId();
//        id = menu.getId();
//        name = menu.getMenu();
//        price = menu.getPrice();
//        food = menu.getFoodId();
//    }
}


//public static MenuListDto of(ShopMenu menu) {
//        return new MenuListDto(menu.getMenu(), menu.getPrice());