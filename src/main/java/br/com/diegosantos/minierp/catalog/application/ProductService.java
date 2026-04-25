package br.com.diegosantos.minierp.catalog.application;

import br.com.diegosantos.minierp.catalog.api.dto.ProductRequest;
import br.com.diegosantos.minierp.catalog.api.dto.ProductResponse;
import br.com.diegosantos.minierp.catalog.domain.Product;
import br.com.diegosantos.minierp.catalog.infrastructure.persistence.ProductRepository;
import br.com.diegosantos.minierp.common.exception.DuplicateResourceException;
import br.com.diegosantos.minierp.common.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        String sku = normalize(request.getSku());

        if (productRepository.existsBySku(sku)) {
            throw new DuplicateResourceException("Produto com SKU já cadastrado");
        }

        Product product = new Product(
                sku,
                normalize(request.getName()),
                normalize(request.getDescription()),
                normalize(request.getUnitOfMeasure())
        );

        return ProductResponse.from(saveHandlingDuplicateSku(product));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listActive() {
        return productRepository.findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return ProductResponse.from(findProduct(id));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findProduct(id);
        String sku = normalize(request.getSku());

        if (productRepository.existsBySkuAndIdNot(sku, id)) {
            throw new DuplicateResourceException("Produto com SKU já cadastrado");
        }

        product.update(
                sku,
                normalize(request.getName()),
                normalize(request.getDescription()),
                normalize(request.getUnitOfMeasure())
        );

        return ProductResponse.from(saveHandlingDuplicateSku(product));
    }

    @Transactional
    public ProductResponse deactivate(Long id) {
        Product product = findProduct(id);
        product.deactivate();
        return ProductResponse.from(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    private Product saveHandlingDuplicateSku(Product product) {
        try {
            return productRepository.saveAndFlush(product);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Produto com SKU já cadastrado");
        }
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }
}