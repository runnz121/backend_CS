package malangcute.bellytime.bellytimeCustomer.cooltime.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.*;

import malangcute.bellytime.bellytimeCustomer.cooltime.repository.CoolTimeRepository;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.domain.DateFormatter;
import malangcute.bellytime.bellytimeCustomer.global.exception.*;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.apache.tomcat.jni.Local;
import org.elasticsearch.index.engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
@ToString
public class CoolTimeService {

    private final TokenAuthentication tokenAuthentication;

    private final TokenProvider tokenProvider;

    private final FoodService foodService;

    private final DateFormatter dateFormatter;

    private final UserRepository userRepository;

    private final CoolTimeRepository coolTimeRepository;


    // 쿨타임 항목 갖고오기(Food, CoolTime 조인)
    // 추후 querydsl로 리펙토링 필요
    @Transactional(readOnly = true)
    public List<GetMyCoolTimeList> getMyList(User user) {
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();

        List<GetMyCoolTimeListIF> listFromRepo = coolTimeRepository.findMyCoolTime(userId);

        List<GetMyCoolTimeList> myList = listFromRepo
                .stream()
               .map(it -> GetMyCoolTimeList.builder()
                       .foodId(it.getFoodId())
                       .foodName(it.getFoodName())
                       .gauge(it.getGauge())
                       .foodImg(it.getFoodImg())
                       .startDate(dateFormatter.localToStringPattern(it.getStartDate()))
                       .duration(it.getDuration())
                       .predictDate(dateFormatter.localToStringPattern(it.getEndDate()))
                       .leftDays(dateFormatter.minusDateLocalDateTime(now,it.getEndDate()))
                       .build()

//                       new GetMyCoolTimeList(
//                        it.getFoodId(),
//                        it.getFoodName(),
//                        it.getGauge(),
//                        it.getDuration(),
//                        it.get
//                        it.getFoodImg(),
//                        dateFormatter.localToStringPattern(it.getEndDate()), // yyyy-mm-dd 형식으로 변환 -> string
//                        dateFormatter.minusDateLocalDateTime(now,it.getEndDate())
                )
                .collect(Collectors.toList());
        return myList;
    }

    //쿨타임 업데이터, 생성
    @Transactional
    public void settingCoolTime(User user, CoolTimeSettingRequest request) throws ParseException {
        Date startDate = dateFormatter.stringToDate(request.getStartDate());
        String duration = String.valueOf(request.getDuration());
        String endDate = dateFormatter.plusDate(startDate, duration);
        Long userId = user.getId();
        Long getFoodId= request.getFoodId();

        Food foodId = foodService.findFoodFromName(request.getFoodName());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime enddate = dateFormatter.stringToLocal(endDate);
        LocalDateTime startdate = dateFormatter.dateToLocal(startDate);
        Long leftDays = dateFormatter.minusDateLocalDateTime(startdate,now);
        Long coolTimeDays = dateFormatter.minusDateLocalDateTime(startdate,enddate);

       String gaugeInit = calGauge(coolTimeDays,leftDays);

        try {
            Optional<CoolTime> exists = coolTimeRepository.findUserIdAndFoodId(userId,getFoodId);
            if (exists.isPresent()) {
                updateCoolTime(userId, getFoodId, startDate, endDate, request.getDuration());
            }
            else {
                createCoolTime(user,foodId, gaugeInit, startDate, endDate, request.getDuration());
            }
        } catch(NotFoundException ex) {
            createCoolTime(user, foodId, gaugeInit, startDate, endDate, request.getDuration());
        }
    }


    //쿨타임 생성하기
    private void createCoolTime(User user, Food foodId, String gauge, Date startDate, String endDate, Integer duration) {
        CoolTime createCoolTime = CoolTime.builder()
                .userId(user)
                .foodId(foodId)
                .startDate(dateFormatter.dateToLocal(startDate))
                .gauge(gauge)
                .endDate(dateFormatter.stringToLocal(endDate))
                .duration(duration)
                .build();

        coolTimeRepository.save(createCoolTime);
    }

    //쿨타임 업데이트하기
    private void  updateCoolTime(Long userId, Long foodId, Date startDate, String endDate, Integer duration) {
        LocalDateTime enddate = dateFormatter.stringToLocal(endDate);
        LocalDateTime startdate = dateFormatter.dateToLocal(startDate);

        LocalDateTime now = LocalDateTime.now();
        Long coolTimeDays = dateFormatter.minusDateLocalDateTime(startdate,enddate);
        System.out.println("cooltimesdays"+coolTimeDays);
        Long leftDays = dateFormatter.minusDateLocalDateTime(startdate,now);
        System.out.println("leftDays" + leftDays);
        String calgauge = calGauge(coolTimeDays, leftDays);
        System.out.println(calgauge);
        coolTimeRepository.updateByUserId(userId,foodId,startdate,enddate,calgauge,duration);
    }

    //나의 쿨타임 삭제하기
    @Transactional
    public void deleteCoolTime(User user, DeleteCoolTimeRequest request){
        Long userId = user.getId();
        Long foodId = request.getFoodId();
        coolTimeRepository.deleteByUserId(userId, foodId);
    }

    // 게이지 반환
    private String calGauge(Long coolTimeDays, Long leftDays) {
        String gauge = "";
        try {
            Integer cal = Math.round(leftDays*100/coolTimeDays);
            gauge = Integer.toString(cal);
        } catch (ArithmeticException ex) {
            log.trace(String.valueOf(ex));
            System.out.println("error here? by zero");
            gauge = "0";
        }
        return gauge;
    }
}
