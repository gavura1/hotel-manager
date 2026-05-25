package sk.umb.hotelmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.umb.hotelmanager.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
