package malangcute.bellytime.bellytimeCustomer.user.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FindMyFriendSearchRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendSearchResponse;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import javax.annotation.PostConstruct;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AwsS3uploader awsS3uploader;
    private final FollowService followService;
    private static final String ERASE_TOKEN = "";

    @PostConstruct
    public void init() {
        followService.setUserService(this);
    }

    //유저 아이디로 유저 찾기(유저 반환)
    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .stream()
                .filter(it -> Objects.equals(it.getId(), userId))
                .findAny()
                .orElseThrow(() -> new UserIdNotFoundException("요청한 유저 아이디가 없습니다"));
    }

    //유저 이메일로 유저 찾기(유저반환)
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(new Email(email))
                .stream()
                .filter(it -> Objects.equals(it.getEmail().getEmail(), email))
                .findAny()
                .orElseThrow(() -> new UserIdNotFoundException("요청한 유저 이메일이 없습니다"));
    }


    //나의 프로필 찾기
    @Transactional(readOnly = true)
    public UserProfileResponse userProfile(User user) {
            return userRepository.findById(user.getId())
                .stream()
                .filter(it -> Objects.equals(it.getId(), user.getId()))
                .findAny()
                .map(UserProfileResponse::of)
                .orElseThrow(() -> new UserIdNotFoundException("선택한 유저 프로필이 없습니다"));
    }

    // 유저 정보 업데이트
    public void userUpdate(UserUpdateRequest userUpdateRequest) throws FailedToConvertImgFileException {
        User user = findUserByEmail(userUpdateRequest.getEmail());
        user.setProfileImg(updateImg(userUpdateRequest.getProfileImg()));
        user.setNickname(userUpdateRequest.getNickname());
        userRepository.save(user);
    }

    // 프로필 이미지 업데이트
    public String updateImg(MultipartFile userImg) throws FailedToConvertImgFileException {
        return awsS3uploader.upload(userImg);
    }

    //닉네임으로 친구 찾기
    public MyFriendSearchResponse findUserByNickname(User host, FindMyFriendSearchRequest request) {
            User followId = findUserByEmail(request.getEmail());
            boolean followStatus = followService.followStatus(host, followId);
            return MyFriendSearchResponse.from(followId, followStatus);
}

    //유저 로그아웃
    public void userLogOut(User user) {
        userRepository.logOutByUserId(user.getId(), ERASE_TOKEN);
    }
}
