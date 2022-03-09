package malangcute.bellytime.bellytimeCustomer.chat.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChatRoomSearchStrategyFactory {

    //전략조건이 여러개 임으로 그중 한개를 반환하기 위해 리스트로 선언함
    private final List<ChatRoomSearchStrategy> strategies;

    public ChatRoomSearchStrategy findStrategy(String type) {
        return strategies.stream()
                .filter(strategy -> !Objects.isNull(type) && strategy.searchBy(type))
                .findAny()
                .orElseThrow(() -> new NotFoundException("맞는 분류조건이 없습니다"));
    }
}
