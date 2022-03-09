package malangcute.bellytime.bellytimeCustomer.user.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.UserPassWordException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class PassWord {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 15;
    private static final String PASSWORD_CHECK = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_CHECK);

    @Column(name = "password", nullable = false)
    private String passWord;

    public PassWord(String passWord){
        validate(passWord);
        this.passWord = passWord;
    }

    public void validate(String passWord){
        validateNull(passWord);
//        validateLength(passWord);
//        validPattern(passWord);
    }

    // 비밀번호 비어있는지 확인
    public void validateNull(String passWord) {
        if (passWord.length() <= 0) {
            throw new UserPassWordException("비밀번호값이 비어 있습니다");
        }
    }


//    //패스워드 길이 검증(최소 8글자 이상)
//    public void validateLength(String passWord) {
//        if (passWord.length() < MIN_LENGTH || passWord.length() > MAX_LENGTH){
//            throw new UserPassWordException("패스워드는 최소 " + MIN_LENGTH + " 글자 이상" + MAX_LENGTH + "이하입니다" + "현재 글자는 " + passWord);
//        }
//    }
//
//    //패스워드 패턴 검증(소문자, 대문자, 숫자 1개 이상)
//    public void validPattern(String passWord) {
//        Matcher matcher = PASSWORD_PATTERN.matcher(passWord);
//
//        if (!matcher.matches()) {
//            throw new UserPassWordException("패스워드는 최소 1개 이상의 소문자, 대문자 숫자를 포하해야 합니다");
//        }
//    }
}
