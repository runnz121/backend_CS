package malangcute.bellytime.bellytimeCustomer.chat.domain;


import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Setter
public class Message {

    private String author;

    private String content;

    private String time;
}
