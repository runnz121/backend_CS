package malangcute.bellytime.bellytimeCustomer.global.auth.service;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.LoginWithIdAndPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RefreshAndAccessTokenResponse;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RegisterWithIdPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityConfig;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityProperties;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserAlreadyExistException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserPassWordException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class LoginService {

    private final UserRepository userRepository;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final TokenProvider tokenProvider;
    private final SecurityConfig securityConfig;

    // 유저 아이디 찾기 -> test
    public UserIdResponse findById(Long id){
        Optional<User> findUser = userRepository.findById(id);
        User user = findUser.orElseThrow(() -> new UserIdNotFoundException("아이디가 없습니다"));
        return UserIdResponse.of(user);
    }


    //아이디 비밀번호 접속시 확인된 유저인지 확인 -> 리프레시, 엑세스 모드 발급
    @Transactional
    public RefreshAndAccessTokenResponse validUser(LoginWithIdAndPassRequest loginWithIdAndPassRequest) throws Exception {
        User requestLoginUser = checkEmail(loginWithIdAndPassRequest.getId());
        checkPassword(requestLoginUser, loginWithIdAndPassRequest.getPassword());

//        //dofilter에러 토큰 파싱해서 처리
        Authentication loginUser = securityConfig.authenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginWithIdAndPassRequest.getId(),
                        loginWithIdAndPassRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(loginUser);
        String refreshToken = tokenProvider.createRefreshToken(loginUser);
        String accessToken = tokenProvider.createAccessToken(loginUser, refreshToken);

        return RefreshAndAccessTokenResponse.of(refreshToken, accessToken);
    }

    // 아이디 비밀번호 기반 유저 저장
    @Transactional
    public void registerNewUser(RegisterWithIdPassRequest registerWithIdPassRequest){
        Optional<User> checkId = Optional.ofNullable(checkEmail(registerWithIdPassRequest.getId()));
        if (checkId.isEmpty()){
            userRepository.save(User.builder()
                            .email(registerWithIdPassRequest.getId())
                            .passWord(PASSWORD_ENCODER.encode(registerWithIdPassRequest.getPassword()))
                            .phoneNumber(registerWithIdPassRequest.getPhoneNumber())
                            .nickName(registerWithIdPassRequest.getNickname())
                            .profileImg(registerWithIdPassRequest.getProfileImg())
                    .build());
        }
        else {
            throw new UserAlreadyExistException("이미 회원가입되어있는 유저입니다");
        }
    }

    //로그인 유저 이메일확인
    public User checkEmail(String email){
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UserIdNotFoundException("가입되어있지 않은 유저입니다"));
    }

    //로그인 유저 비밀번호 확인
    public void checkPassword(User user, String password){
        if (!user.getPassword().equals(PASSWORD_ENCODER.encode(password))) {
            throw new UserPassWordException("비밀번호가 잘못되었습니다");
        }
    }
}
