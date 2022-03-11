package malangcute.bellytime.bellytimeCustomer.user.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.UserNickNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class NickName {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 7;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    public NickName(String nickName){
        //validate(nickName);
        this.nickName = nickName;
    }


    private void validate(String nickName){
        validLength(nickName);
    }

    //닉네임길이 검증(최소 2 글자이상 7글자 이하)
    private void validLength(String nickName) {
        if (nickName.length() < MIN_LENGTH || nickName.length() > MAX_LENGTH) {
            throw new UserNickNameException("닉네임은" + MIN_LENGTH + "이상" + MAX_LENGTH + "이하여야 합니다");
        }
    }
}
