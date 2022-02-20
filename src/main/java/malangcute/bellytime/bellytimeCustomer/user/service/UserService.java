package malangcute.bellytime.bellytimeCustomer.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FindMyFriendSearchRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendSearchResponse;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowShopRepository;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.global.exception.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoUserException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.NickName;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFollowShopResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AwsS3uploader awsS3uploader;

    //유저 아이디로 유저 찾기(유저 반환)
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .stream()
                .filter(it -> Objects.equals(it.getId(), userId))
                .findAny()
                .orElseThrow(() -> new UserIdNotFoundException("요청한 유저 아이디가 없습니다"));
    }

    //유저 이메일로 유저 찾기(유저반환
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(new Email(email))
                .stream()
                .filter(it -> Objects.equals(it.getEmail().getEmail(), email))
                .findAny()
                .orElseThrow(() -> new UserIdNotFoundException("요청한 유저 이메일이 없습니다"));
    }


    //나의 프로필 찾기
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
//        Optional<User> userId = userRepository.findByEmail(new Email(userUpdateRequest.getEmail()));
//        User user = userId.orElseThrow(() -> new NoUserException("해당하는 유저가 없습니다"));
        User user = findUserByEmail(userUpdateRequest.getEmail());
        String updateFileUrl = updateImg(userUpdateRequest.getProfileImg());
        String updateNickName = userUpdateRequest.getNickname();
        user.setProfileImg(updateFileUrl);
        user.setNickname(updateNickName);
        userRepository.save(user);
    }

    // 프로필 이미지 업데이트
    public String updateImg(MultipartFile userImg) throws FailedToConvertImgFileException {
        String fileUrl = awsS3uploader.upload(userImg);
        return fileUrl;
    }

    //닉네임으로 친구 찾기
    public MyFriendSearchResponse findUserByNickname(FindMyFriendSearchRequest request) {
        return userRepository.findByEmail(new Email(request.getEmail()))
                .stream()
                .map(MyFriendSearchResponse::from)
                .findFirst().orElseThrow(() -> new NotFoundException("유저가 없습니다"));
    }
}
