package br.com.diegosantos.minierp.identity.api;

import br.com.diegosantos.minierp.common.exception.InvalidCredentialsException;
import br.com.diegosantos.minierp.identity.api.dto.LoginRequest;
import br.com.diegosantos.minierp.identity.api.dto.LoginResponse;
import br.com.diegosantos.minierp.identity.api.dto.MeResponse;
import br.com.diegosantos.minierp.identity.application.AuthenticateUserService;
import br.com.diegosantos.minierp.identity.application.GetCurrentUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticateUserService authenticateUserService;
    private final GetCurrentUserService getCurrentUserService;

    public AuthController(
            AuthenticateUserService authenticateUserService,
            GetCurrentUserService getCurrentUserService
    ) {
        this.authenticateUserService = authenticateUserService;
        this.getCurrentUserService = getCurrentUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authenticateUserService.execute(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new InvalidCredentialsException("Token invalido ou ausente");
        }

        MeResponse response = getCurrentUserService.execute(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
