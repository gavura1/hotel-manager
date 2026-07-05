package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.UserHotelsRequestDto;
import sk.umb.hotelmanager.dto.UserResponseDto;
import sk.umb.hotelmanager.dto.UserRoleRequestDto;
import sk.umb.hotelmanager.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    // test CI trigger
    private final AdminService adminService;

    @GetMapping("/pouzivatelia")
    public List<UserResponseDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/pouzivatelia/{id}/rola")
    public UserResponseDto updateRole(@PathVariable Long id,
                                      @RequestBody UserRoleRequestDto request) {
        return adminService.updateUserRole(id, request);
    }

    @PutMapping("/pouzivatelia/{id}/hotely")
    public UserResponseDto updateHotels(@PathVariable Long id,
                                        @RequestBody UserHotelsRequestDto request) {
        return adminService.updateUserHotels(id, request);
    }
}