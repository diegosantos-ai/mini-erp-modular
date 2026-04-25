package br.com.diegosantos.minierp.catalog.application;

import br.com.diegosantos.minierp.catalog.api.dto.SupplierRequest;
import br.com.diegosantos.minierp.catalog.api.dto.SupplierResponse;
import br.com.diegosantos.minierp.catalog.domain.Supplier;
import br.com.diegosantos.minierp.catalog.infrastructure.persistence.SupplierRepository;
import br.com.diegosantos.minierp.common.exception.DuplicateResourceException;
import br.com.diegosantos.minierp.common.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public SupplierResponse create(SupplierRequest request) {
        String documentNumber = normalize(request.getDocumentNumber());

        if (supplierRepository.existsByDocumentNumber(documentNumber)) {
            throw new DuplicateResourceException("Fornecedor com documento já cadastrado");
        }

        Supplier supplier = new Supplier(
                normalize(request.getName()),
                documentNumber,
                normalize(request.getEmail()),
                normalize(request.getPhone())
        );

        return SupplierResponse.from(saveHandlingDuplicateDocument(supplier));
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> listActive() {
        return supplierRepository.findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(SupplierResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse getById(Long id) {
        return SupplierResponse.from(findSupplier(id));
    }

    @Transactional
    public SupplierResponse update(Long id, SupplierRequest request) {
        Supplier supplier = findSupplier(id);
        String documentNumber = normalize(request.getDocumentNumber());

        if (supplierRepository.existsByDocumentNumberAndIdNot(documentNumber, id)) {
            throw new DuplicateResourceException("Fornecedor com documento já cadastrado");
        }

        supplier.update(
                normalize(request.getName()),
                documentNumber,
                normalize(request.getEmail()),
                normalize(request.getPhone())
        );

        return SupplierResponse.from(saveHandlingDuplicateDocument(supplier));
    }

    @Transactional
    public SupplierResponse deactivate(Long id) {
        Supplier supplier = findSupplier(id);
        supplier.deactivate();
        return SupplierResponse.from(supplier);
    }

    private Supplier findSupplier(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));
    }

    private Supplier saveHandlingDuplicateDocument(Supplier supplier) {
        try {
            return supplierRepository.saveAndFlush(supplier);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Fornecedor com documento já cadastrado");
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
