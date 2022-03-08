package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomFindWithContactIdAndTypeRequest {

    private String contactId;

    private String type;
}
