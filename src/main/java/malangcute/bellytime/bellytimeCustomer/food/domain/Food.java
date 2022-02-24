package malangcute.bellytime.bellytimeCustomer.food.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.shop.domain.ShopMenu;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food")
@Document(indexName ="food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "food_name")
    private String name;

    //@Column(name = "food_img")
    private String image;

    @JsonBackReference
    @OneToMany(mappedBy = "foodId")
    private List<ShopMenu> shopMenu = new ArrayList<>();

    @Builder
    @PersistenceConstructor
    public Food(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }


    public static Food of (Food list) {
       return new Food(list.getId(), list.getName(), list.getImage());
    }
}
