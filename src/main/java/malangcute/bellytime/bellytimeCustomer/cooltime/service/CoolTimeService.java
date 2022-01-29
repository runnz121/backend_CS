package malangcute.bellytime.bellytimeCustomer.cooltime.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeList;

import malangcute.bellytime.bellytimeCustomer.cooltime.dto.MyListResponse;
import malangcute.bellytime.bellytimeCustomer.cooltime.repository.CoolTimeRepository;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.exception.CalculatorException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoUserException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CoolTimeService {


    private static final String REFRESH_TOKEN = "refreshToken";

    private final TokenAuthentication tokenAuthentication;

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    private final CoolTimeRepository coolTimeRepository;





    // 쿨타임 항목 갖고오기(Food, CoolTime 조인)
    // 추후 querydsl로 리펙토링 필요
    public List<MyListResponse> getMyList(HttpServletRequest httpServletRequest) {
        String refreshToken = tokenAuthentication.getRefreshFromRequest(httpServletRequest);
        String userEmail = tokenProvider.getUserIdFromToken(refreshToken);
        User finduser = userRepository.findByEmail(new Email(userEmail)).orElseThrow(() -> new UserIdNotFoundException("no userid found"));
        Long userId = finduser.getId();

        System.out.println(refreshToken);
        System.out.println(userEmail);
        System.out.println(finduser);
        System.out.println("expected userId" + userId);

        String mylist = coolTimeRepository.findMyCoolTime(userId);
        System.out.println(mylist);


//        List<GetMyCoolTimeList> myList = coolTimeRepository
//                .findMyCoolTime(userId).stream()
//                .map(objects -> new GetMyCoolTimeList(
//                        (String)objects[0],//food id
//                        (String)objects[1],//food name
//                        (String)objects[2],//food img
//                        (String)objects[3],//cooltime gauge
//                        (LocalDateTime) objects[4],//cooltime start
//                        (LocalDateTime) objects[5]))//cooltime end
//                .collect(Collectors.toList());

//        List<Long> calDays = calPredictDate(myList.indexOf(4));
//        System.out.println("myist" + myList);
        return null;

    }


    //현자 날짜 기준으로 만기날짜 조회
    private List<Long> calPredictDate(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime nowLocal = LocalDateTime.now();
        Long coolTimeDays= ChronoUnit.DAYS.between(startDate, endDate);
        Long leftDays = ChronoUnit.DAYS.between(nowLocal, endDate);
        return new ArrayList<>(Arrays.asList(coolTimeDays, leftDays));
    }

    // 게이지 반환
    private String calGauge(Long coolTimeDays, Long leftDays) {
        String gauge = "";
        try {
            long cal = (leftDays/coolTimeDays) * 100;
            gauge = Long.toString(cal);
            return gauge;
        } catch (CalculatorException ex) {
            log.trace(String.valueOf(ex));
            System.out.println(ex);
            return "error";
        }

    }


}
