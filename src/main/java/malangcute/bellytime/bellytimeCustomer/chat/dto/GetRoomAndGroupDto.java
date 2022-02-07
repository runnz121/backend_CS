package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetRoomAndGroupDto {
    private String roomId;

    private String groupId;

    public static GetRoomAndGroupDto of(String roomId, String groupId) {
        return new GetRoomAndGroupDto(roomId, groupId);
    }
}
