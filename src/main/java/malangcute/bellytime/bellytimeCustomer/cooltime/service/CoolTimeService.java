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


    private static final String REFRESH_TOKEN = "refreshToken";

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
        String userEmail = user.getEmail().getEmail();
        User finduser = userRepository.findByEmail(new Email(userEmail)).orElseThrow(() -> new UserIdNotFoundException("no userid found"));
        List<GetMyCoolTimeListIF> listFromRepo = coolTimeRepository.findMyCoolTime(finduser);
        List<GetMyCoolTimeList> myList = calPredictDate(listFromRepo);
        return myList;
    }

    //쿨타임 업데이터, 생성
    @Transactional
    public void settingCoolTime(User user, CoolTimeSettingRequest request) throws ParseException {
        Date startDate = dateFormatter.stringToDate(request.getStartDate());
        String endDate = dateFormatter.plusDate(startDate, request.getDuration());
        Food foodId = foodService.findFoodFromName(request.getFoodName());
        Long leftDays = Long.valueOf(request.getDuration());
        String gaugeInit = calGauge(0L,0L);

        try {
            coolTimeRepository.findByUserIdAndFoodId(user,foodId).orElseThrow(()->new NotFoundException("쿨타임이 없습니다"));
           // updateCoolTime(user, foodId, startDate, endDate);
        } catch(NotFoundException ex) {
            createCoolTime(user, foodId, gaugeInit, startDate, endDate);
        }
    }


    //쿨타임 생성하기
    private void createCoolTime(User user, Food foodId, String gauge, Date startDate, String endDate) {
        CoolTime createCoolTime = CoolTime.builder()
                .userId(user)
                .foodId(foodId)
                .startDate(dateFormatter.dateToLocal(startDate))
                .gauge(gauge)
                .endDate(dateFormatter.LocalDateToLocalDateTime(dateFormatter.stringToLocal(endDate)))
                .build();

        coolTimeRepository.save(createCoolTime);
    }

//    private void  updateCoolTime(User user, Food foodId, Date startDate, String duration) {
//        Long coolTimeDays = Long.valueOf(duration);
//        String calgauge = calGauge(coolTimeDays, );
//
//
//    }

    // 게이지 반환
    private String calGauge(Long coolTimeDays, Long leftDays) {
        String gauge = "";
        try {
            long cal = (leftDays/coolTimeDays) * 100;
            gauge = Long.toString(cal);
        } catch (ArithmeticException ex) {
            log.trace(String.valueOf(ex));
            System.out.println("error here? by zero");
            gauge = "0";
        }
        return gauge;
    }



    //나의 쿨타임 삭제하기
    public void deleteCoolTime(User user, DeleteCoolTimeRequest request){
        Long userId = user.getId();
        Long foodId = request.getFoodId();
        coolTimeRepository.deleteByUserId(userId, foodId);
    }





    //현자 날짜 기준으로 만기날짜 조회
    private List<GetMyCoolTimeList> calPredictDate(List<GetMyCoolTimeListIF> list) {

        LocalDateTime nowLocal = LocalDateTime.now();

        List<GetMyCoolTimeList> returnList = new ArrayList<>();


//        for (int i = 0; i < list.size(); i++) {
//            LocalDateTime startdate = list.get(i).getStartDate();
//            LocalDateTime enddate = list.get(i).getEndDate();
//
//            String foodname = list.get(i).getFoodName();
//            String foodid = list.get(i).getFoodId();
//            String foodimg = list.get(i).getFoodImg();
//
//            Long coolTimeDays= ChronoUnit.DAYS.between(startdate, enddate);
//            Long leftDays = ChronoUnit.DAYS.between(nowLocal, enddate);
//
//            System.out.println("items" + startdate +"//" + enddate+"//" + foodid+"//" +foodimg+"//" +coolTimeDays +"//" +leftDays);
//
//            String gauge = calGauge(coolTimeDays, leftDays);
//
//            System.out.println("gauge" + gauge);
//            GetMyCoolTimeList cal = GetMyCoolTimeList.builder()
//                    .foodId(foodid)
//                    .name(foodname)
//                    .foodImg(foodimg)
//                    .predictDate(enddate)
//                    .leftDays(leftDays)
//                    .gauge(gauge)
//                    .build();
//            returnList.add(cal);
//        }
//        System.out.println("return lists"+ returnList);
        return returnList;
    }
}
