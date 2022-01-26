package malangcute.bellytime.bellytimeCustomer.global.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.*;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserAlreadyExistException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserPassWordException;
import malangcute.bellytime.bellytimeCustomer.user.domain.AuthProvider;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.domain.UserImg;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final String REFRESH_TOKEN = "refreshToken";
    private final TokenProvider tokenProvider;


    // 유저 아이디 찾기 -> test
    public UserIdResponse findById(Long id){
        Optional<User> findUser = userRepository.findById(id);
        User user = findUser.orElseThrow(() -> new UserIdNotFoundException("아이디가 없습니다"));
        return UserIdResponse.of(user);
    }


    //아이디 비밀번호 접속시 확인된 유저인지 확인 -> 리프레시, 엑세스 모드 발급
    @Transactional
    public RefreshAndAccessTokenResponse validUser(LoginWithIdAndPassRequest loginWithIdAndPassRequest) {
        User requestLoginUser = checkEmail(loginWithIdAndPassRequest.getEmail());
        checkPassword(requestLoginUser, loginWithIdAndPassRequest.getPassword());
        String refreshToken = tokenProvider.createRefreshToken(loginWithIdAndPassRequest.getEmail());
        String accessToken = tokenProvider.createAccessToken(loginWithIdAndPassRequest.getEmail(), refreshToken);
        requestLoginUser.setRefreshToken(refreshToken);
        userRepository.save(requestLoginUser);
        return RefreshAndAccessTokenResponse.of(accessToken, refreshToken);
    }

    // 아이디 비밀번호 기반 유저 저장
    @Transactional
    public void registerNewUser(RegisterWithIdPassRequest registerWithIdPassRequest){
        if (userRepository.existsByEmail(new Email(registerWithIdPassRequest.getEmail()))) {
            throw new UserAlreadyExistException("이미 가입된 유저입니다");
        }
           User user = User.builder()
                            .email(registerWithIdPassRequest.getEmail())
                            .passWord(PASSWORD_ENCODER.encode(registerWithIdPassRequest.getPassword()))
                            .phoneNumber(registerWithIdPassRequest.getPhoneNumber())
                            .nickName(registerWithIdPassRequest.getNickname())
                           // .profileImg(registerWithIdPassRequest.getProfileImg())
                    .build();
           user.setAuthProvider(AuthProvider.IDPASS);
           user.setImg(UserImg.builder().name("test").url("test").build());
           userRepository.save(user);
    }

    // 엑세스 토큰 재발급
    @Transactional(readOnly = true)
    public AccessTokenResponseDto checkAccessToken(RequestAccessTokenCheck requestAccessTokenCheck, HttpServletRequest httpServletRequest) {
        String checkToken = requestAccessTokenCheck.getAccessToken();

        System.out.println("checkToken " + checkToken);
        Optional<Cookie> cookie = CookieUtils.getCookie(httpServletRequest, REFRESH_TOKEN);
        Cookie getCookie = cookie.orElseThrow(() -> new NoCookieException("쿠키가 없습니다"));
        String refreshToken = getCookie.getValue();

        String userId = tokenProvider.getUserIdFromToken(refreshToken);
        User newOne = userRepository.findByEmail(new Email(userId)).orElseThrow(()->new UserIdNotFoundException("유저가 존재하지 않습니다"));
        String newAccessToken = tokenProvider.createAccessToken(newOne.getEmail().getEmail(), refreshToken);
        System.out.println("newaccesstoken : " + newAccessToken);
        return AccessTokenResponseDto.of(newAccessToken);
    }

    public AccessTokenResponseDto refreshAccess(String token) {
        return AccessTokenResponseDto.of(token);
    }



    //로그인 유저 이메일확인
    public User checkEmail(String email){
        return userRepository.findByEmail(new Email(email)).
                orElseThrow(() -> new UserIdNotFoundException("가입되어있지 않은 유저입니다"));
    }

    //로그인 유저 비밀번호 확인
    public void checkPassword(User user, String password){
        if (!PASSWORD_ENCODER.matches(password, user.getPassword())) {
            throw new UserPassWordException("비밀번호가 잘못되었습니다");
        }
    }
}
