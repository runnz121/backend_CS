package malangcute.bellytime.bellytimeCustomer.user.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.NickName;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserIdResponse {

    private Long id;

    private NickName nickName;

    private Email mail;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime createdDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime modifiedDate;


    // 매개변수를 받아 적합한 타입의 인스턴스를 반환(집계메서드)
    @Builder
    public static UserIdResponse of (User user){
        return new UserIdResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
