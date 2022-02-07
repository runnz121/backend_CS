package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class InfoSenderDto {

    private String roomId;

    private String groupId;

    public static InfoSenderDto of(String roomId, String groupId) {
        return new InfoSenderDto(roomId, groupId);
    }
}
