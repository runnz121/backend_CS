package malangcute.bellytime.bellytimeCustomer.global.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.*;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.global.exception.*;
import malangcute.bellytime.bellytimeCustomer.user.domain.AuthProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@AllArgsConstructor
@Transactional
@Service
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final UserService userService;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final TokenProvider tokenProvider;
    private final AwsS3uploader awsS3uploader;


    //아이디 비밀번호 접속시 확인된 유저인지 확인 -> 리프레시, 엑세스 모드 발급
    public RefreshAndAccessTokenResponse validUser(LoginWithIdAndPassRequest loginWithIdAndPassRequest) {
        User requestLoginUser = userService.findUserByEmail(loginWithIdAndPassRequest.getEmail());
        checkPassword(requestLoginUser, loginWithIdAndPassRequest.getPassword());
        String refreshToken = tokenProvider.createRefreshToken(loginWithIdAndPassRequest.getEmail());
        String accessToken = tokenProvider.createAccessToken(loginWithIdAndPassRequest.getEmail(), refreshToken);
        requestLoginUser.setRefreshToken(refreshToken);
        userRepository.save(requestLoginUser);
        return RefreshAndAccessTokenResponse.of(accessToken, refreshToken);
    }

    // 아이디 비밀번호 기반 유저 저장
    public void registerNewUser(RegisterWithIdPassRequest registerWithIdPassRequest) throws FailedToConvertImgFileException {

        if (existUserCheck(new Email(registerWithIdPassRequest.getEmail()))) {
            throw new UserAlreadyExistException("이미 가입된 유저입니다");
        }
           User user = User.builder()
                            .email(registerWithIdPassRequest.getEmail())
                            .passWord(PASSWORD_ENCODER.encode(registerWithIdPassRequest.getPassword()))
                            .phoneNumber(registerWithIdPassRequest.getPhoneNumber())
                            .nickName(registerWithIdPassRequest.getNickname())
                    .build();
           if (!Objects.isNull(registerWithIdPassRequest.getProfileImg()))
           {
               user.setProfileImg(awsS3uploader.upload(registerWithIdPassRequest.getProfileImg()));
           }
           user.setAuthProvider(AuthProvider.IDPASS);
           userRepository.save(user);
    }

    //이미 존재하는지 유저 확인
    @Transactional(readOnly = true)
    public boolean existUserCheck(Email email) {
       return userRepository.existsByEmail(new Email(email.getEmail()));
    }

    //로그인 유저 비밀번호 확인
    @Transactional(readOnly = true)
    public void checkPassword(User user, String password){
        if (!PASSWORD_ENCODER.matches(password, user.getPassword())) {
            throw new UserPassWordException("비밀번호가 잘못되었습니다");
        }
    }
}
