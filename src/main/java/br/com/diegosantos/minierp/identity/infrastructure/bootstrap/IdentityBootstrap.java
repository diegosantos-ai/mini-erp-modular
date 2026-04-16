package br.com.diegosantos.minierp.identity.infrastructure.bootstrap;

import br.com.diegosantos.minierp.identity.domain.Role;
import br.com.diegosantos.minierp.identity.domain.RoleName;
import br.com.diegosantos.minierp.identity.domain.User;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.RoleRepository;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class IdentityBootstrap implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IdentityBootstrap(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        ensureRoleExists(RoleName.ADMIN);
        ensureRoleExists(RoleName.SOLICITANTE);
        ensureRoleExists(RoleName.APROVADOR);
        ensureRoleExists(RoleName.COMPRADOR);
        ensureRoleExists(RoleName.ALMOXARIFE);
        ensureRoleExists(RoleName.GESTOR);

        if (userRepository.findByEmail("admin@minierp.local").isEmpty()) {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new IllegalStateException("Role ADMIN não encontrada"));

            User admin = new User(
                    "Administrador",
                    "admin@minierp.local",
                    passwordEncoder.encode("Admin@123")
            );

            admin.getRoles().add(adminRole);

            userRepository.save(admin);
        }
    }

    private void ensureRoleExists(RoleName roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }
}