package malangcute.bellytime.bellytimeCustomer.global.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class LoginService {

    private final TokenProvider tokenProvider;
}
