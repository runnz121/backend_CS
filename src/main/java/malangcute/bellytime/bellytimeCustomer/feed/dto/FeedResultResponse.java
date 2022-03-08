package malangcute.bellytime.bellytimeCustomer.feed.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.feed.domain.Feed;
import malangcute.bellytime.bellytimeCustomer.feed.domain.FeedImg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResultResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private String title;

    private String content;

    private List<String> images = new ArrayList<>();


    public static FeedResultResponse of (Feed feed) {

        List<String> list = feed.getFeedImgs().stream()
                .map(FeedImg::getImage)
                .collect(Collectors.toList());

        return new FeedResultResponse (
                feed.getShop().getId(),
                feed.getShop().getName(),
                feed.getRepresentImg(),
                feed.getTitle(),
                feed.getContent(),
                list
        );
    }
}
