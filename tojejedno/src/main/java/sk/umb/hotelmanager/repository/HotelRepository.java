package sk.umb.hotelmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.umb.hotelmanager.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
