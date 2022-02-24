package malangcute.bellytime.bellytimeCustomer.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShopMenu  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menu;

    private Long price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food foodId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shopId;

}
