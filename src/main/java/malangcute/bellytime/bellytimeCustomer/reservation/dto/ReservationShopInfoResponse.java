package malangcute.bellytime.bellytimeCustomer.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class ReservationShopInfoResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private LocalDateTime reservedDate;

    private Long personnel;

    private Long dDay;

    public ReservationShopInfoResponse(Long id, String name, String image, LocalDateTime reservedDate, Long reservedPeople) {
        this.shopId = id;
        this.shopName = name;
        this.profileImg =image;
        this.reservedDate = reservedDate;
        this.personnel = reservedPeople;
    }

    public ReservationShopInfoResponse(Long id, String name, String image, LocalDateTime reservedDate, Long reservedPeople, Long dDay) {
        this.shopId = id;
        this.shopName = name;
        this.profileImg =image;
        this.reservedDate = reservedDate;
        this.personnel = reservedPeople;
        this.dDay = dDay;
    }

    public static ReservationShopInfoResponse of (Reservation reservation) {
        return new ReservationShopInfoResponse(
                reservation.getShopId().getId(),
                reservation.getShopId().getName(),
                reservation.getShopId().getImage(),
                reservation.getReservedDate(),
                reservation.getReservedPeople()
                );
    }

    public static ReservationShopInfoResponse state (ReservationShopInfoResponse reservation, Long dDAy) {
        return new ReservationShopInfoResponse(
                reservation.getShopId(),
                reservation.getShopName(),
                reservation.getProfileImg(),
                reservation.getReservedDate(),
                reservation.getPersonnel(),
                dDAy
        );
    }
}
