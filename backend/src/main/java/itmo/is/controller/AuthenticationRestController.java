package itmo.is.controller;

import itmo.is.dto.authentication.JwtResponse;
import itmo.is.dto.authentication.LoginRequest;
import itmo.is.dto.authentication.RegisterRequest;
import itmo.is.dto.authentication.UserDto;
import itmo.is.service.security.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "*")

    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

        @PostMapping("/auth/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/auth/register-admin")
    @CrossOrigin(origins = "*")

    public ResponseEntity<JwtResponse> registerAdmin(@RequestBody RegisterRequest request) {
        if (authenticationService.hasRegisteredAdmins()) {
            log.info("First admin is already registered, register others as disabled");
            authenticationService.submitAdminRegistrationRequest(request);
            return ResponseEntity.ok().build();
        } else {
            log.info("Register first admin");
            return ResponseEntity.ok(authenticationService.registerFirstAdmin(request));

        }


    }

    @PutMapping("admin/registration-requests/{userId}")
    @CrossOrigin(origins = "*")

    public ResponseEntity<Void> approveAdminRegistrationRequest(@PathVariable Long userId) {
        authenticationService.approveAdminRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("admin/registration-requests/{userId}")
    @CrossOrigin(origins = "*")

    public ResponseEntity<Void> rejectAdminRegistrationRequest(@PathVariable Long userId) {
        authenticationService.rejectAdminRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("admin/registration-requests")
    @CrossOrigin(origins = "*")

    public ResponseEntity<Page<UserDto>> getPendingRegistrationRequests(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(authenticationService.getPendingRegistrationRequests(pageable));
    }
}