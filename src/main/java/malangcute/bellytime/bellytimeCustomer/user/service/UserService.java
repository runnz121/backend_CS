package malangcute.bellytime.bellytimeCustomer.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.global.exception.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoUserException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.NickName;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AwsS3uploader awsS3uploader;

    public void userUpdate(UserUpdateRequest userUpdateRequest) throws FailedToConvertImgFileException {
        Optional<User> userId = userRepository.findByEmail(new Email(userUpdateRequest.getEmail()));
        User user = userId.orElseThrow(() -> new NoUserException("해당하는 유저가 없습니다"));

        String updateFileUrl = updateImg(userUpdateRequest.getProfileImg());
        String updateNickName = userUpdateRequest.getNickname();

        user.setProfileImg(updateFileUrl);
        user.setNickname(updateNickName);
        userRepository.save(user);
    }



    public String updateImg(MultipartFile userImg) throws FailedToConvertImgFileException {
        String fileUrl = awsS3uploader.upload(userImg);
        return fileUrl;
    }

}
