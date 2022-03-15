package malangcute.bellytime.bellytimeCustomer.cooltime.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDateList;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDayList2;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalTodayFoodList2;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalTodayFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCheckRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeGetMyFriends;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeGetMyFriendsIF;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeSettingRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeShopRecommendResponse;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.DeleteCoolTimeRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeList;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeListIF;
import malangcute.bellytime.bellytimeCustomer.cooltime.repository.CoolTimeCalFactory;
import malangcute.bellytime.bellytimeCustomer.cooltime.repository.CoolTimeCalStrategy;
import malangcute.bellytime.bellytimeCustomer.cooltime.repository.CoolTimeRepository;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.global.domain.DataFormatter;
import malangcute.bellytime.bellytimeCustomer.global.domain.DateFormatterImpl;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopCoolTimeSearchStrategyFactory;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CoolTimeService {

    private final FoodService foodService;

    private final DataFormatter dateFormatterImpl;

    private final CoolTimeRepository coolTimeRepository;

    private final ShopCoolTimeSearchStrategyFactory factory;

    private final CoolTimeCalFactory coolTimeCalFactory;




    // 쿨타임 항목 갖고오기(Food, CoolTime 조인)
    // 추후 querydsl로 리펙토링 필요
    @Transactional(readOnly = true)
    public List<GetMyCoolTimeList> getMyList(User user) {
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();

        List<GetMyCoolTimeListIF> listFromRepo = coolTimeRepository.findMyCoolTime(userId);

        return listFromRepo
            .stream()
            .sorted(Comparator.comparing(GetMyCoolTimeListIF::getGauge))
            .map(it -> GetMyCoolTimeList.of(it,
                    dateFormatterImpl.localToStringPattern(it.getStartDate()),
                    dateFormatterImpl.localToStringPattern(it.getEndDate()),
                    dateFormatterImpl.minusDateLocalDateTime(now, it.getEndDate())))
            .collect(Collectors.toList());

        // return listFromRepo
        //         .stream()
        //         .map(it -> GetMyCoolTimeList.builder()
        //                .foodId(it.getFoodId())
        //                .foodName(it.getFoodName())
        //                .gauge(it.getGauge())
        //                .foodImg(it.getFoodImg())
        //                .startDate(dateFormatterImpl.localToStringPattern(it.getStartDate()))
        //                .duration(it.getDuration())
        //                .predictDate(dateFormatterImpl.localToStringPattern(it.getEndDate()))
        //                .leftDays(dateFormatterImpl.minusDateLocalDateTime(now,it.getEndDate()))
        //                .build()
        //         )
        //         .collect(Collectors.toList());
    }

    //쿨타임 업데이터, 생성
    @Transactional
    public void settingCoolTime(User user, CoolTimeSettingRequest request) throws ParseException {
        Date startDate = dateFormatterImpl.stringToDate(request.getStartDate());
        String duration = String.valueOf(request.getDuration());
        String endDate = dateFormatterImpl.plusDate(startDate, duration);
        Long userId = user.getId();
        Long getFoodId = request.getFoodId();

        Food foodId = foodService.findFoodFromName(request.getFoodName());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime enddate = dateFormatterImpl.stringToLocal(endDate);
        LocalDateTime startdate = dateFormatterImpl.dateToLocal(startDate);
        Long leftDays = dateFormatterImpl.minusDateLocalDateTime(startdate, now);
        Long coolTimeDays = dateFormatterImpl.minusDateLocalDateTime(startdate, enddate);

       String gaugeInit = calGauge(coolTimeDays, leftDays);

        try {
            Optional<CoolTime> exists = coolTimeRepository.findUserIdAndFoodId(userId, getFoodId);
            if (exists.isPresent()) {
                updateCoolTime(userId, getFoodId, startDate, endDate, request.getDuration());
            } else {
                createCoolTime(user, foodId, gaugeInit, startDate, endDate, request.getDuration());
            }
        } catch (NotFoundException ex) {
            createCoolTime(user, foodId, gaugeInit, startDate, endDate, request.getDuration());
        }
    }


    //쿨타임 생성하기
    private void createCoolTime(User user, Food foodId, String gauge, Date startDate, String endDate, Integer duration) {
        CoolTime createCoolTime = CoolTime.builder()
                .userId(user)
                .foodId(foodId)
                .startDate(dateFormatterImpl.dateToLocal(startDate))
                .gauge(gauge)
                .endDate(dateFormatterImpl.stringToLocal(endDate))
                .duration(duration)
                .eat(false)
                .build();
        coolTimeRepository.save(createCoolTime);
    }

    //쿨타임 업데이트하기
    private void  updateCoolTime(Long userId, Long foodId, Date startDate, String endDate, Integer duration) {
        LocalDateTime enddate = dateFormatterImpl.stringToLocal(endDate);
        LocalDateTime startdate = dateFormatterImpl.dateToLocal(startDate);
        LocalDateTime now = LocalDateTime.now();
        Long coolTimeDays = dateFormatterImpl.minusDateLocalDateTime(startdate, enddate);
        Long leftDays = dateFormatterImpl.minusDateLocalDateTime(startdate, now);
        String calgauge = calGauge(coolTimeDays, leftDays);
        coolTimeRepository.updateByUserId(userId, foodId, startdate, enddate, calgauge, duration);
    }

    //나의 쿨타임 삭제하기
    @Transactional
    public void deleteCoolTime(User user, DeleteCoolTimeRequest request) {
        Long userId = user.getId();
        Long foodId = request.getFoodId();
        coolTimeRepository.deleteByUserId(userId, foodId);
    }

    // 게이지 반환
    private String calGauge(Long coolTimeDays, Long leftDays) {
        String gauge = "";
        try {
            Integer cal = Math.round(leftDays * 100 / coolTimeDays);
            gauge = Integer.toString(cal);
        } catch (ArithmeticException ex) {
            log.trace(String.valueOf(ex));
            gauge = "0";
        }
        return gauge;
    }


    //쿨타임 전략패턴
    public CoolTimeCalListResponse1 selected(User user, Long month, Long year, String check) {

        CoolTimeCalFactory.CoolTimeStatus status = CoolTimeCalFactory.CoolTimeStatus.of(check);

        return coolTimeCalFactory.find(status).coolTimeCalByFilter(user, month, year);
    }


    //쿨타임 eat, 상태 업데이트
    public void updateCoolTimeEatCheck(User user, List<CoolTimeCheckRequest> request) {
        for (CoolTimeCheckRequest request1 : request) {
            coolTimeRepository.updateEatByUserId(request1.isEat(), user.getId(), request1.getFoodId());
        }
    }


    //친구 쿨타임 갖고오기 (쿨타임 추천 페이지)
    public List<CoolTimeGetMyFriends> getMyFriendsListByFood (User user, Long foodId) {
        return  coolTimeRepository.findMyCoolTimeFriends(user.getId(), foodId)
                .stream()
                .sorted(Comparator.comparing(CoolTimeGetMyFriendsIF::getGauge).reversed())
                .map(CoolTimeGetMyFriends::of)
                .collect(Collectors.toList());
    }

    //필터로 쿨타임 반환
    @Transactional(readOnly = true)
    public List<CoolTimeShopRecommendResponse> getShopListFilterBy(User user, Long foodId, String filter, Double lat, Double lon, Pageable pageable) {
        return factory.searchStrategy(filter).selectStrategy(user, foodId, lat, lon, pageable);
    }
}
