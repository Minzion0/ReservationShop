package com.example.reservationshop.service;

import com.example.reservationshop.entity.CustomerEntity;
import com.example.reservationshop.entity.ReservationShopEntity;
import com.example.reservationshop.entity.ShopEntity;
import com.example.reservationshop.model.Reservation;
import com.example.reservationshop.repository.ReservationShopRepository;
import com.example.reservationshop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopReservationService {
    private final ReservationShopRepository reservationShopRepository;
    private final ShopRepository shopRepository;

    public ReservationShopEntity makeReservation(Reservation.Request request, CustomerEntity customerEntity){
        ShopEntity shopEntity = getShopEntity(request.getShopId());

        LocalDateTime requestDateTime = LocalDateTime.of(request.getReservationDate(), request.getReservationTime());
        LocalDateTime endTime = requestDateTime.plusHours(1);

        Optional<List<ReservationShopEntity>> timeBetween = reservationShopRepository.findByShopIdAndReservationTimeBetween(shopEntity, requestDateTime, endTime);

        if (timeBetween.isEmpty()||timeBetween.get().size()<shopEntity.getTableCount()){
          return reservationShopRepository.save(ReservationShopEntity.from(shopEntity,customerEntity,requestDateTime));
        }else {
            throw new RuntimeException(requestDateTime.toString()+"해당 시간의 예약이 불가능 합니다.");
        }

    }

    public List<Reservation.Response> serchReservationShop(Long shopId, LocalDate date) {
        ShopEntity shopEntity = getShopEntity(shopId);
        LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
        LocalDateTime endTime = startTime.plusHours(23).plusMinutes(59);
        List<ReservationShopEntity> reservationShopEntities = reservationShopRepository.findByShopIdAndReservationTimeBetween(shopEntity, startTime, endTime).orElse(null);

        return reservationShopEntities.stream().map(item -> Reservation.Response.builder().reservationId(item.getId()).reservationTime(item.getReservationTime()).build()).collect(Collectors.toList());


    }
    @Transactional(readOnly = true)
    public void shopCheckIn(CustomerEntity customerEntity, Long reservationId) {
        ReservationShopEntity reservationShopEntity = reservationShopRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("예약내역을 찾을수 없습니다."));

        if (!Objects.equals(reservationShopEntity.getCustomerId().getId(), customerEntity.getId())){
            throw new RuntimeException("예약자 본인만 체크인 가능합니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationTime = reservationShopEntity.getReservationTime();
        LocalDateTime checkInStartTime = reservationTime.minusMinutes(10);

        if (now.isBefore(checkInStartTime) || now.isAfter(reservationTime)) {
            throw new RuntimeException("체크인 가능 시간이 아닙니다. 체크인은 예약 시간 10분 전부터 예약 시간까지 가능합니다.");
        }

        reservationShopEntity.checkIn();
    }

    private ShopEntity getShopEntity(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("가계를 찾을 수 없습니다."));
    }
}
