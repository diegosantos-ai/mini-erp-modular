package br.com.diegosantos.minierp.catalog.api.dto;

import br.com.diegosantos.minierp.catalog.domain.Product;

import java.time.LocalDateTime;

public class ProductResponse {

    private final Long id;
    private final String sku;
    private final String name;
    private final String description;
    private final String unitOfMeasure;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductResponse(
            Long id,
            String sku,
            String name,
            String description,
            String unitOfMeasure,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getUnitOfMeasure(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
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
