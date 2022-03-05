package malangcute.bellytime.bellytimeCustomer.reservation.service;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.domain.DateFormatterImpl;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoReservationException;
import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import malangcute.bellytime.bellytimeCustomer.reservation.dto.ReservationShopInfoResponse;
import malangcute.bellytime.bellytimeCustomer.reservation.repository.ReservationRepository;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final DateFormatterImpl dateFormatterImpl;

    //예약 아이디로 예약 반환
    public Reservation findByReservationId(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoReservationException("예약건이 없습니다"));
    }


    //유저아이디로 예약목록 갖고오기
    public List<ReservationShopInfoResponse> myReservationList(User user) {
        return reservationRepository.findByUserId(user)
                .stream()
                .map(ReservationShopInfoResponse::of)
                .collect(Collectors.toList());
    }

/*
예약 상태 반환에 대해선 수정이 필요함 -> 단일 것만 에 대한 것인지.....
만약 단체 건이면 차라리 리스트 반환시 같이 보내주는게 나음
 */
    //예약 상태 변환반환(유저입장에서는 -> 기본으로 pending, 사장이 이걸 업데이트 하면 다시 바뀜)
    //예약상태가 complete일 경우 반환
    public List<ReservationShopInfoResponse> reservationStateAccepted(User user) {
        return reservationRepository.findByUserId(user)
                .stream()
                .filter(it -> it.getReservedState().equals("complete"))
                .map(it -> ReservationShopInfoResponse.state(
                        ReservationShopInfoResponse.of(it),
                        dateFormatterImpl.leftDays(it.getReservedDate())))
                .collect(Collectors.toList());
    }
    //예약상태가 pending이나 cancle일 경우
    public List<ReservationShopInfoResponse> reservationStateNoAccepted(User user) {
        return reservationRepository.findByUserId(user)
                .stream()
                .filter(it -> !it.getReservedState().equals("complete"))
                .map(ReservationShopInfoResponse::of)
                .collect(Collectors.toList());
    }


}
