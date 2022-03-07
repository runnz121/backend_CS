package malangcute.bellytime.bellytimeCustomer.global.domain;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface DataFormatter {

     Long leftDays(LocalDateTime compare);

     Long minusDateLocalDateTime(LocalDateTime longer, LocalDateTime shorter);

     String localToStringPattern(LocalDateTime localDateTime);

     Date localDateTimeToDate(LocalDateTime localDateTime);

     Date stringToDate(String stringDate) throws ParseException;

     String plusDate(Date startDate, String duration);

     String LocalDateTimeHour (LocalDateTime localDateTime);

     LocalDateTime stringToLocal(String stringDate);

     LocalDateTime dateToLocal(Date date);

     LocalDateTime LocalDateToLocalDateTime(LocalDate localDate);

     String TimeStampHour(Timestamp timestamp);


}
