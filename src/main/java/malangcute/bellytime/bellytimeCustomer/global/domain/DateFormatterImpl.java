package malangcute.bellytime.bellytimeCustomer.global.domain;

import org.springframework.stereotype.Component;

import javax.swing.text.DateFormatter;
import java.sql.Time;
import java.sql.Timestamp;
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
public class DateFormatterImpl implements DataFormatter{

    @Override
    public String localToStringPattern(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public LocalDateTime stringToLocal(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateTime = LocalDate.parse(stringDate, formatter);
    return LocalDateToLocalDateTime(localDateTime);
    }

    @Override
    public Timestamp localDateTimeToDate(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    @Override
    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(stringDate);
    }

    @Override
    public String plusDate(Date startDate, String duration) {
        Integer appendDays = Integer.valueOf(duration);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, appendDays);
        return simpleDateFormat.format(cal.getTime());
    }

    @Override
    public Long minusDateLocalDateTime(LocalDateTime longer, LocalDateTime shorter) {
        return ChronoUnit.DAYS.between(longer, shorter);
    }

    @Override
    public LocalDateTime dateToLocal(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public LocalDateTime LocalDateToLocalDateTime(LocalDate localDate){
        return localDate.atStartOfDay();
    }

    @Override
    public Long leftDays(LocalDateTime compare) {
        LocalDate today = LocalDate.now();
        LocalDateTime todayLocal = LocalDateToLocalDateTime(today);
        return minusDateLocalDateTime(compare, todayLocal);
    }

    @Override
    public String LocalDateTimeHour(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        return localDateTime.format(formatter);
    }

    @Override
    public String TimeStampHour(Timestamp timestamp) {
        LocalDateTime toLocal = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        return toLocal.format(formatter);
    }
}
