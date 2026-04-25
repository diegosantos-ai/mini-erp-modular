package br.com.diegosantos.minierp.catalog.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SupplierRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 160, message = "Nome deve ter no máximo 160 caracteres")
    private String name;

    @NotBlank(message = "Documento é obrigatório")
    @Size(max = 32, message = "Documento deve ter no máximo 32 caracteres")
    private String documentNumber;

    @Email(message = "E-mail inválido")
    @Size(max = 160, message = "E-mail deve ter no máximo 160 caracteres")
    private String email;

    @Size(max = 40, message = "Telefone deve ter no máximo 40 caracteres")
    private String phone;

    public String getName() {
        return name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
