package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.food.dto.ResultFoodListDto;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultListDto {

    private List<String> resultList1;


    public static ResultListDto of (String resultList) {
        return new ResultListDto();
    }
}
