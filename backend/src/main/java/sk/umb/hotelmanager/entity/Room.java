package sk.umb.hotelmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import sk.umb.hotelmanager.enums.RoomStatus;
import sk.umb.hotelmanager.enums.RoomType;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus roomStatus;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

}
