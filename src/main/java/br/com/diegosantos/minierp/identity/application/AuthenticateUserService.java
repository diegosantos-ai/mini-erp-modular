package br.com.diegosantos.minierp.identity.application;

import br.com.diegosantos.minierp.identity.api.dto.LoginResponse;
import br.com.diegosantos.minierp.identity.domain.User;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        if (!user.isAtivo()) {
            throw new IllegalArgumentException("Usuário inativo");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        return new LoginResponse("token-placeholder", "Bearer");
    }
}