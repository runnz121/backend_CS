package malangcute.bellytime.bellytimeCustomer.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecentListResponse {

    private Set<String> recent = new HashSet<>();

    public static SearchRecentListResponse of (Set<String> list) {
        return new SearchRecentListResponse(list);
    }
}
