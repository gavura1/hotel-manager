package sk.umb.hotelmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import sk.umb.hotelmanager.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    private String note;


    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
