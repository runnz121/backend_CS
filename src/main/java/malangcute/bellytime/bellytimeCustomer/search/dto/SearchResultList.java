package malangcute.bellytime.bellytimeCustomer.search.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchResultList {

    private List<String> searchResult;

    public static SearchResultList of (List<String> result) {
        return new SearchResultList(result);
    }
}
