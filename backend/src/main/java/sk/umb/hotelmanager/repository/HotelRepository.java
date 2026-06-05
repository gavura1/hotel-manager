package sk.umb.hotelmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.umb.hotelmanager.entity.Hotel;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByManagerId(Long managerId);
}
