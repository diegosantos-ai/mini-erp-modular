package br.com.diegosantos.minierp.catalog.api;

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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SupplierApiIntegrationTest {

    private static final String ADMIN_EMAIL = "admin-suppliers@minierp.local";
    private static final String ADMIN_LOGIN_VALUE = String.join("-", "fixture", "admin", "login");

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

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("security.jwt.secret", SupplierApiIntegrationTest::jwtFixtureSecret);
        registry.add("security.jwt.expiration", () -> "3600");
        registry.add("identity.bootstrap.admin.enabled", () -> "false");
    }

    @Test
    void shouldCreateSupplier() throws Exception {
        String token = createAdminToken();

        mockMvc.perform(post("/api/suppliers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierJson("Fornecedor Alpha", "12345678000190", "alpha@minierp.local", "11999990000")))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/api/suppliers/")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Fornecedor Alpha"))
                .andExpect(jsonPath("$.documentNumber").value("12345678000190"))
                .andExpect(jsonPath("$.email").value("alpha@minierp.local"))
                .andExpect(jsonPath("$.phone").value("11999990000"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldGetSupplierById() throws Exception {
        String token = createAdminToken();
        long supplierId = createSupplier(token, "Fornecedor Beta", "22345678000190");

        mockMvc.perform(get("/api/suppliers/{id}", supplierId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(supplierId))
                .andExpect(jsonPath("$.name").value("Fornecedor Beta"))
                .andExpect(jsonPath("$.documentNumber").value("22345678000190"));
    }

    @Test
    void shouldListOnlyActiveSuppliers() throws Exception {
        String token = createAdminToken();
        long inactiveSupplierId = createSupplier(token, "Fornecedor Inativo", "32345678000190");
        createSupplier(token, "Fornecedor Ativo", "42345678000190");

        mockMvc.perform(patch("/api/suppliers/{id}/deactivate", inactiveSupplierId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));

        mockMvc.perform(get("/api/suppliers")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Fornecedor Ativo"))
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @Test
    void shouldUpdateSupplier() throws Exception {
        String token = createAdminToken();
        long supplierId = createSupplier(token, "Fornecedor Original", "52345678000190");

        mockMvc.perform(put("/api/suppliers/{id}", supplierId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierJson("Fornecedor Atualizado", "52345678000191", "novo@minierp.local", "11888880000")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(supplierId))
                .andExpect(jsonPath("$.name").value("Fornecedor Atualizado"))
                .andExpect(jsonPath("$.documentNumber").value("52345678000191"))
                .andExpect(jsonPath("$.email").value("novo@minierp.local"))
                .andExpect(jsonPath("$.phone").value("11888880000"));
    }

    @Test
    void shouldDeactivateSupplier() throws Exception {
        String token = createAdminToken();
        long supplierId = createSupplier(token, "Fornecedor Gama", "62345678000190");

        mockMvc.perform(patch("/api/suppliers/{id}/deactivate", supplierId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(supplierId))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void shouldRejectRequiredFields() throws Exception {
        String token = createAdminToken();

        mockMvc.perform(post("/api/suppliers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierJson("", "", "email-invalido", null)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Dados de entrada inválidos"))
                .andExpect(jsonPath("$.fields.name").value("Nome é obrigatório"))
                .andExpect(jsonPath("$.fields.documentNumber").value("Documento é obrigatório"))
                .andExpect(jsonPath("$.fields.email").value("E-mail inválido"));
    }

    @Test
    void shouldRejectOversizedFields() throws Exception {
        String token = createAdminToken();

        mockMvc.perform(post("/api/suppliers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierJson(
                                "A".repeat(161),
                                "1".repeat(33),
                                "a".repeat(64) + "@" + "b".repeat(63) + "." + "c".repeat(28) + ".com",
                                "1".repeat(41)
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.name").value("Nome deve ter no máximo 160 caracteres"))
                .andExpect(jsonPath("$.fields.documentNumber").value("Documento deve ter no máximo 32 caracteres"))
                .andExpect(jsonPath("$.fields.email").value("E-mail deve ter no máximo 160 caracteres"))
                .andExpect(jsonPath("$.fields.phone").value("Telefone deve ter no máximo 40 caracteres"));
    }

    @Test
    void shouldRejectDuplicatedDocumentNumber() throws Exception {
        String token = createAdminToken();
        createSupplier(token, "Fornecedor Delta", "72345678000190");

        mockMvc.perform(post("/api/suppliers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierJson("Fornecedor Duplicado", "72345678000190", null, null)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Fornecedor com documento já cadastrado"));
    }

    private String createAdminToken() {
        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseGet(() -> roleRepository.saveAndFlush(new Role(RoleName.ADMIN)));

        User admin = new User(
                "Administrador",
                ADMIN_EMAIL,
                passwordEncoder.encode(ADMIN_LOGIN_VALUE)
        );

        admin.getRoles().add(adminRole);
        userRepository.saveAndFlush(admin);

        return jwtService.generateToken(admin.getEmail());
    }

    private long createSupplier(String token, String name, String documentNumber) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/suppliers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierJson(name, documentNumber, null, null)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("id").asLong();
    }

    private String supplierJson(String name, String documentNumber, String email, String phone) throws Exception {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", name);
        payload.put("documentNumber", documentNumber);
        payload.put("email", email);
        payload.put("phone", phone);
        return objectMapper.writeValueAsString(payload);
    }

    private static String jwtFixtureSecret() {
        byte[] key = new byte[32];

        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) (i + 1);
        }

        return Base64.getEncoder().encodeToString(key);
    }
}
