package malangcute.bellytime.bellytimeCustomer.shop.dto;


import malangcute.bellytime.bellytimeCustomer.shop.domain.ShopMenu;

import java.math.BigDecimal;
import java.util.List;

public interface ShopDetailDto {

    Long getId();

    String getName();

    String getImg();

    Long getBellscore();

    BigDecimal getLatitude();

    BigDecimal getLongitude();

    String getAddress();

    String getRuntime();

    Long getFollowerCount();

    List<MenusList> getMenu();
}
