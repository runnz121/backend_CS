package malangcute.bellytime.bellytimeCustomer.shop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="shop")
@Document(indexName ="shop") // 이 클래스가 엘라스틱 서치에 맵핑됨을 확인
public class Shop { // 엘라스틱 서치는 localdatetime 컨버터시 에러남 따라서 baseentity extends 처리 안함 -> mapepr로 해결할 수 잇음 추후 해셜할 것

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private Long bellscore;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String address;

    private String runtime;

    @Formula("(select count(*) from follow_shop where follow_shop.shop_id=id)")
    private int follower;


    @OneToMany(mappedBy = "shopId", cascade = CascadeType.ALL)
    private List<ShopMenu> shopId = new ArrayList<>();


    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<FollowShop> followShops = new ArrayList<>();


    @OneToMany(mappedBy = "shopId", cascade = CascadeType.ALL)
    private List<Reservation> reservationShop = new ArrayList<>();

    @Builder
    @PersistenceConstructor //ES DB에 저장된 document가 aggregate로 재구성됨 (생성자에 붙여야함)
    public Shop (Long id, String name, String image, Long bellscore, BigDecimal latitude, BigDecimal longitude,String address, String runtime, int follower) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.bellscore = bellscore;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.runtime = runtime;
        this.follower = follower;
    }
}
