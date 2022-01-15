package malangcute.bellytime.bellytimeCustomer.user.service;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.elasticsearch.common.util.CancellableThreads;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 아이디 찾기
    public UserIdResponse findById(Long id){
        Optional<User> findUser = userRepository.findById(id);
        User user = findUser.orElseThrow(() -> new UserIdNotFoundException("아이디가 없습니다"));
        return UserIdResponse.of(user);
    }
}
