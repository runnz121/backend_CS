package malangcute.bellytime.bellytimeCustomer.chat.dto;


import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Setter
public class MessageDto {

    private String roomName;

    private String message;

    private String writer;

   // private String groupId;
}
