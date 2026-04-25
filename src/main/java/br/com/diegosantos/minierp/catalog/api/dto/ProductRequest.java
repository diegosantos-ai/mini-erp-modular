package br.com.diegosantos.minierp.catalog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductRequest {

    @NotBlank(message = "O SKU é obrigatório")
    @Size(max = 100, message = "O SKU deve ter no máximo 100 caracteres")
    private String sku;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 160, message = "O nome deve ter no máximo 160 caracteres")
    private String name;

    private String description;

    @NotBlank(message = "A unidade de medida é obrigatória")
    @Size(max = 40, message = "A unidade de medida deve ter no máximo 40 caracteres")
    private String unitOfMeasure;

    protected ProductRequest() {
    }

    public ProductRequest(String sku, String name, String description, String unitOfMeasure) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
