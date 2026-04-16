package br.com.diegosantos.minierp.identity.api;

import br.com.diegosantos.minierp.identity.api.dto.LoginRequest;
import br.com.diegosantos.minierp.identity.api.dto.LoginResponse;
import br.com.diegosantos.minierp.identity.application.AuthenticateUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateUserService authenticateUserService;

    public AuthController(AuthenticateUserService authenticateUserService) {
        this.authenticateUserService = authenticateUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authenticateUserService.execute(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(response);
    }
}