package malangcute.bellytime.bellytimeCustomer.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.feed.domain.Feed;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedListResponse {

    private Long shopId;

    private Long postId;

    private String shopName;

    private String representImg;

    private String title;

    public static FeedListResponse of(Feed feed) {
        return new FeedListResponse(
                feed.getShop().getId(),
                feed.getId(),
                feed.getShop().getName(),
                feed.getRepresentImg(),
                feed.getTitle());
    }
}
