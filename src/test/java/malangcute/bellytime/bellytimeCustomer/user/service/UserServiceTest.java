package malangcute.bellytime.bellytimeCustomer.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FindMyFriendSearchRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendSearchResponse;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@DisplayName("유저 컨트롤러 테스트")
@Slf4j
class UserServiceTest extends TestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final MockMultipartFile 요청_프로필_이미지 =
        new MockMultipartFile("profileImg", "cats.JPEG", MediaType.IMAGE_JPEG_VALUE, "cats".getBytes());

    private static final FindMyFriendSearchRequest 찾을_이메일 =
        new FindMyFriendSearchRequest("kuku@kuku.com");

    private static final UserUpdateRequest 업데이트요청_유저 =
        new UserUpdateRequest("test@test.com", "미미", 요청_프로필_이미지);

    private User 가입된_유저;

    private User 찾을_친구;

    @BeforeEach
    public void setUpUser1() {
        가입된_유저 = User.builder()
                .passWord("test")
                .email("test@test.com")
                .nickName("testnickname")
                .profileImg("cloudfront_img")
                .phoneNumber("010-0000-0000")
                .build();
        userRepository.save(가입된_유저);
    }

    @BeforeEach
    public void setUpUser2() {
        찾을_친구 = User.builder()
            .passWord("test")
            .email("kuku@kuku.com")
            .nickName("쿠쿠")
            .profileImg("cloudfront_kuku_img")
            .phoneNumber("010-1234-1234")
            .build();
        userRepository.save(찾을_친구);
    }


    @DisplayName("유저 이메일로 유저 찾기(유저반환)")
    @Test
    void findUserByEmail() {
        //when
        User findUser = userService.findUserByEmail("test@test.com");

        //then
        assertThat(findUser.getEmail()).isEqualTo(가입된_유저.getEmail());
    }

    @DisplayName("나의 프로필 찾기(닉네임과, 유저 이미지를 반환)")
    @Test
    void userProfile() {
        //when
        UserProfileResponse myprofile = userService.userProfile(가입된_유저);

        //then
        assertThat(myprofile.getProfileImg()).isEqualTo(가입된_유저.getProfileImg());
        assertThat(myprofile.getName()).isEqualTo(가입된_유저.getNickname().getNickName());
    }

    @DisplayName("유저 정보 업데이트")
    @Test
    void userUpdate() throws FailedToConvertImgFileException {

        userService.userUpdate(업데이트요청_유저);

        assertThat(가입된_유저.getNickname().getNickName()).isEqualTo("미미");
        assertThat(가입된_유저.getProfileImg()).isNotEqualTo("cloudfront_img");
    }

    @DisplayName("프로필 이미지 업데이트")
    @Test
    void updateImg() throws FailedToConvertImgFileException {

        String imgUrl = userService.updateImg(요청_프로필_이미지);

        assertThat(imgUrl).isNotEmpty();
        assertThat(imgUrl).containsPattern("https://d1utjczuqj7mhr.cloudfront.net");
    }

    @DisplayName("닉네임으로 친구 찾기")
    @Test
    void findUserByNickname() {

        MyFriendSearchResponse 찾은_친구 = userService.findUserByNickname(가입된_유저, 찾을_이메일);

        assertThat(찾은_친구.getNickName()).isEqualTo("쿠쿠");
        assertThat(찾은_친구.getProfileImg()).isEqualTo("cloudfront_kuku_img");
    }
}