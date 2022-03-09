package malangcute.bellytime.bellytimeCustomer.user.domain;


import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.UserEmailException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
public class Email {

    private static final String EMAIL_CHECK = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_CHECK);

    @Column(name = "email", nullable = false)
    private String email;

   public Email(String email){
        validate(email);
        this.email = email;
    }

    private void validate(String email){
        validPattern(email);
    }

    // 이메일 형식 확인
    private void validPattern(String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.matches()){
            throw new UserEmailException("이메일 형식이 올바르지 않습니다");
        }
    }
}
