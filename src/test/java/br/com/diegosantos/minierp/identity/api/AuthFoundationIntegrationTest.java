package br.com.diegosantos.minierp.identity.api;

import br.com.diegosantos.minierp.identity.domain.Role;
import br.com.diegosantos.minierp.identity.domain.RoleName;
import br.com.diegosantos.minierp.identity.domain.User;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.RoleRepository;
import br.com.diegosantos.minierp.identity.infrastructure.persistence.UserRepository;
import br.com.diegosantos.minierp.identity.infrastructure.security.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(properties = {
        "security.jwt.secret=VGhpcy1pcy1hLXRlc3Qtc2VjcmV0LWZvci1qd3QtdGVzdHMtMzItYnl0ZXMh",
        "security.jwt.expiration=3600",
        "identity.bootstrap.admin.enabled=false"
})
class AuthFoundationIntegrationTest {

    private static final String ADMIN_EMAIL = "admin@minierp.local";
    private static final String ADMIN_PASSWORD = "Admin@123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldReturnHealthWhenAuthorizationHeaderContainsInvalidToken() throws Exception {
        mockMvc.perform(get("/actuator/health")
                        .header("Authorization", "Bearer token-invalido-para-health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void shouldAllowLoginWhenAuthorizationHeaderContainsInvalidToken() throws Exception {
        createAdminUser(true);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token-invalido-para-login")
                        .content("""
                                {
                                  "email": "admin@minierp.local",
                                  "password": "Admin@123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.type").value("Bearer"));
    }

    @Test
    void shouldAuthenticateAndReturnCurrentUser() throws Exception {
        createAdminUser(true);

        String token = performLoginAndExtractToken();

        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.nome").value("Administrador"))
                .andExpect(jsonPath("$.roles[0]").value("ADMIN"));
    }

    @Test
    void shouldReturnUnauthorizedWhenAccessingCurrentUserWithoutToken() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Token ausente, inválido ou expirado"));
    }

    @Test
    void shouldReturnUnauthorizedWhenAccessingCurrentUserWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer token-invalido-para-protected-endpoint"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Token ausente, inválido ou expirado"));
    }

    @Test
    void shouldReturnUnauthorizedWhenInactiveUserUsesOldValidToken() throws Exception {
        User user = createAdminUser(true);
        String token = jwtService.generateToken(user.getEmail());

        user.desativar();
        userRepository.saveAndFlush(user);

        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Usuário inativo"));
    }

    @Test
    void shouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        createAdminUser(true);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@minierp.local",
                                  "password": "senha-errada"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Credenciais inválidas"));
    }

    private User createAdminUser(boolean active) {
        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseGet(() -> roleRepository.saveAndFlush(new Role(RoleName.ADMIN)));

        User admin = new User(
                "Administrador",
                ADMIN_EMAIL,
                passwordEncoder.encode(ADMIN_PASSWORD)
        );

        admin.getRoles().add(adminRole);

        if (!active) {
            admin.desativar();
        }

        return userRepository.saveAndFlush(admin);
    }

    private String performLoginAndExtractToken() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@minierp.local",
                                  "password": "Admin@123"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("token").asText();
    }
}
