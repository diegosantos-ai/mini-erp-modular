package br.com.diegosantos.minierp.identity.application;

import br.com.diegosantos.minierp.common.exception.InvalidCredentialsException;
import br.com.diegosantos.minierp.identity.api.dto.LoginResponse;
import br.com.diegosantos.minierp.identity.domain.User;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AuthenticateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Credenciais inválidas"));

        if (!user.isAtivo()) {
            throw new InvalidCredentialsException("Usuário inativo");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        return new LoginResponse(
                "token-placeholder",
                "Bearer",
                OffsetDateTime.now().plusHours(1)
        );
    }
}