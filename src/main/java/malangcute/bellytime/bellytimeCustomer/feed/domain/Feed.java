package malangcute.bellytime.bellytimeCustomer.feed.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed_post")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String representImg;

    private String title;

    private String content;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedImg> feedImgs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    @Builder
    public Feed(Long id, String title, String representImg, List<FeedImg> imgs, Shop shop, String content) {
        this.title = title;
        this.id = id;
        this.representImg = representImg;
        this.feedImgs = imgs;
        this.shop = shop;
        this.content = content;
    }
}
