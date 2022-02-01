package malangcute.bellytime.bellytimeCustomer.food.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Table(name = "food")
public class Food extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "food_name")
    private String name;

    //@Column(name = "food_img")
    private String image;

    @Builder
    public Food(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
