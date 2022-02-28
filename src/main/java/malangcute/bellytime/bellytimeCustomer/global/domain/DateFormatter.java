package malangcute.bellytime.bellytimeCustomer.global.domain;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateFormatter {

    public String localToStringPattern(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    public LocalDateTime stringToLocal(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateTime = LocalDate.parse(stringDate, formatter);
    return LocalDateToLocalDateTime(localDateTime);
    }

    //LocalDateTime to Date
    public Date localDateTimeToDate(LocalDateTime localDateTime) {
        Date date = java.sql.Timestamp.valueOf(localDateTime);
        return date;
    }

    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(stringDate);
        return date;
    }


    //날짜 더하기
    public String plusDate(Date startDate, String duration) {
        Integer appendDays = Integer.valueOf(duration);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, appendDays);
        String plusdates = simpleDateFormat.format(cal.getTime());
        return plusdates;
    }

    // localdatetime으로 두 날짜 사이 계산 -> long days 반환
    public Long minusDateLocalDateTime(LocalDateTime longer, LocalDateTime shorter) {
        Long days = ChronoUnit.DAYS.between(longer, shorter);
        return days;
    }

    public LocalDateTime dateToLocal(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return localDateTime;
    }

    public LocalDateTime LocalDateToLocalDateTime(LocalDate localDate){
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime;
    }

    //오늘날짜(localdate)와 비교 날짜군 (localdatetime)을 받아서 남은일 수 반환
    public Long leftDays(LocalDateTime compare) {
        LocalDate today = LocalDate.now();
        LocalDateTime todayLocal = LocalDateToLocalDateTime(today);
        return minusDateLocalDateTime(compare, todayLocal);
    }
}
