package malangcute.bellytime.bellytimeCustomer.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ResultListDto;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class AssembleListDto {

    private List<String> resultList;

    public static ResultListDto of (String name) {
        return new ResultListDto();
    }
}
