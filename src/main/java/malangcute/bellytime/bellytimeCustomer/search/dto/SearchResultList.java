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

    private Set<String> recentSearch;

    private List<String> searchResult;

    public static SearchResultList of (Set<String> recent, List<String> result) {
        return new SearchResultList(recent, result);
    }
}
