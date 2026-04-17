package br.com.diegosantos.minierp.identity.application;

import br.com.diegosantos.minierp.common.exception.AuthenticatedUserNotFoundException;
import br.com.diegosantos.minierp.identity.api.dto.MeResponse;
import br.com.diegosantos.minierp.identity.domain.User;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetCurrentUserService {

    private final UserRepository userRepository;

    public GetCurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public MeResponse execute(String email) {
        User user = userRepository.findWithRolesByEmail(email)
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Usuário autenticado não encontrado"));

        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return new MeResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                roles
        );
    }
}
