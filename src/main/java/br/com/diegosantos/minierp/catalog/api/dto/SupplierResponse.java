package br.com.diegosantos.minierp.catalog.api.dto;

import br.com.diegosantos.minierp.catalog.domain.Supplier;

import java.time.LocalDateTime;

public class SupplierResponse {

    private final Long id;
    private final String name;
    private final String documentNumber;
    private final String email;
    private final String phone;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SupplierResponse(
            Long id,
            String name,
            String documentNumber,
            String email,
            String phone,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.documentNumber = documentNumber;
        this.email = email;
        this.phone = phone;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SupplierResponse from(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getDocumentNumber(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.isActive(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

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

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
