package malangcute.bellytime.bellytimeCustomer.global.auth;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoUserException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class DummyLoginArgResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequireLogin.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception {


        return userService.findUserByEmail("test@test.com");
    }
}
