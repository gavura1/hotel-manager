package sk.umb.hotelmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.umb.hotelmanager.entity.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomHotelManagerId(Long managerId);

    List<Booking> findByRoomHotelId(Long hotelId);
}