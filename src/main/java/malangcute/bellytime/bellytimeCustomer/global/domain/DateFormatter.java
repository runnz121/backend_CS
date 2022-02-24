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

    // 2018-08-04T10:11:30 형식으로 들어옴
    public String localToStringPattern(LocalDateTime localDateTime) {

        //String dateString = "2018-08-04T10:11:30";

        //LocalDateTime parsedLocalDateTime = LocalDateTime.parse(localDateTime);


        String yyyyMMdd = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String yyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
//        String MM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("MM"));
    return yyyyMMdd;
    }


    // yyyy-MM-dd로 들어옴
    public LocalDateTime stringToLocal(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       // LocalDateTime localDateTime = LocalDateTime.parse(stringDate, formatter).toLocalDate().atStartOfDay();
        //LocalDateTime d = LocalDateTime.parse("2016-10-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDate localDateTime = LocalDate.parse(stringDate, formatter);
    return LocalDateToLocalDateTime(localDateTime);
    }


    //LocalDateTime to Date
    public Date localDateTimeToDate(LocalDateTime localDateTime) {
        Date date = java.sql.Timestamp.valueOf(localDateTime);
        return date;
    }

    //
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
}
