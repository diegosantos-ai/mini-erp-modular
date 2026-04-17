package br.com.diegosantos.minierp.identity.infrastructure.bootstrap;

import br.com.diegosantos.minierp.identity.domain.Role;
import br.com.diegosantos.minierp.identity.domain.RoleName;
import br.com.diegosantos.minierp.identity.domain.User;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.RoleRepository;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class IdentityBootstrap implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean adminBootstrapEnabled;
    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;

    public IdentityBootstrap(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${identity.bootstrap.admin.enabled:false}") boolean adminBootstrapEnabled,
            @Value("${identity.bootstrap.admin.name:Administrador}") String adminName,
            @Value("${identity.bootstrap.admin.email:}") String adminEmail,
            @Value("${identity.bootstrap.admin.password:}") String adminPassword
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminBootstrapEnabled = adminBootstrapEnabled;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        ensureRoleExists(RoleName.ADMIN);
        ensureRoleExists(RoleName.SOLICITANTE);
        ensureRoleExists(RoleName.APROVADOR);
        ensureRoleExists(RoleName.COMPRADOR);
        ensureRoleExists(RoleName.ALMOXARIFE);
        ensureRoleExists(RoleName.GESTOR);

        if (!adminBootstrapEnabled) {
            return;
        }

        validateAdminBootstrapConfiguration();
        ensureAdminExists();
    }

    private void ensureRoleExists(RoleName roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            try {
                roleRepository.save(new Role(roleName));
            } catch (DataIntegrityViolationException ex) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    throw ex;
                }
            }
        }
    }

    private void ensureAdminExists() {
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Role ADMIN não encontrada"));

        User admin = new User(
                adminName,
                adminEmail,
                passwordEncoder.encode(adminPassword)
        );

        admin.getRoles().add(adminRole);

        try {
            userRepository.save(admin);
        } catch (DataIntegrityViolationException ex) {
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                throw ex;
            }
        }
    }

    private void validateAdminBootstrapConfiguration() {
        if (isBlank(adminEmail) || isBlank(adminPassword)) {
            throw new IllegalStateException(
                    "Configure identity.bootstrap.admin.email e identity.bootstrap.admin.password para habilitar o bootstrap do admin"
            );
        }
    }

    private boolean isBlank(String value) {
        return Objects.isNull(value) || value.isBlank();
    }
}
