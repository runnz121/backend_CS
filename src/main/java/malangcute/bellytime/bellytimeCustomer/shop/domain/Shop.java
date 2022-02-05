package malangcute.bellytime.bellytimeCustomer.shop.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name ="shop")
@Document(indexName ="shop") // 이 클래스가 엘라스틱 서치에 맵핑됨을 확인
public class Shop { // 엘라스틱 서치는 localdatetime 컨버터시 에러남 따라서 baseentity extends 처리 안함 -> mapepr로 해결할 수 잇음 추후 해셜할 것

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private Long score;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String address;

    private String runtime;


    @Builder
    @PersistenceConstructor //ES DB에 저장된 document가 aggregate로 재구성됨 (생성자에 붙여야함)
    public Shop (Long id, String name, String image, Long score, BigDecimal latitude, BigDecimal longitude,String address, String runtime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.score = score;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.runtime = runtime;
    }
}
