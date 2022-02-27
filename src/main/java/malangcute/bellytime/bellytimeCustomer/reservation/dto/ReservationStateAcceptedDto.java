//package malangcute.bellytime.bellytimeCustomer.reservation.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
//
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ReservationStateAcceptedDto {
//
//
//    private Long shopId;
//
//    private String shopName;
//
//    private String profileImg;
//
//    private LocalDateTime reservedDate;
//
//    private Long personnel;
//
//    private Long dDay;
//
//    public static ReservationStateAcceptedDto of (Reservation reservation, Long day) {
//        return new ReservationStateAcceptedDto(
//                reservation.getShopId().getId(),
//                reservation.getShopId().getName(),
//                reservation.getShopId().getImage(),
//                reservation.getReservedDate(),
//                reservation.getReservedPeople(),
//                day
//        );
//    }
//}
