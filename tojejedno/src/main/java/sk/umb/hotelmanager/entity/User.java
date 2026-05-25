package sk.umb.hotelmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sk.umb.hotelmanager.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "manager")
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    public User() {
        this.createdAt = LocalDateTime.now();
    }
}
