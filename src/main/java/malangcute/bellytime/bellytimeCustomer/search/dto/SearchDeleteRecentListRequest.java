package malangcute.bellytime.bellytimeCustomer.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class SearchDeleteRecentListRequest {

    private List<String> recentDel = new ArrayList<>();
}
