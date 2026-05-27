package sk.umb.hotelmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.umb.hotelmanager.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
    SELECT r FROM Room r
    WHERE r.hotel.id = :hotelId
    AND r.roomStatus = 'AVAILABLE'
    AND r.id NOT IN (
        SELECT b.room.id FROM Booking b
        WHERE b.status = 'CONFIRMED'
        AND b.fromDate < :checkOut
        AND b.toDate > :checkIn
    )
    """)
    List<Room> findAvailableRooms(@Param("hotelId")Long hotelId,
                                  @Param("checkIn")LocalDate checkIn,
                                  @Param("checkOut")LocalDate checkOut);

    List<Room> findByHotelId(Long hotelId);
}
