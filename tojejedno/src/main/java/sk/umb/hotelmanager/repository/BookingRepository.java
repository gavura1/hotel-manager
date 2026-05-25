package sk.umb.hotelmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.umb.hotelmanager.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
