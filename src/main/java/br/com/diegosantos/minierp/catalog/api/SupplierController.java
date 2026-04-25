package br.com.diegosantos.minierp.catalog.api;

import br.com.diegosantos.minierp.catalog.api.dto.SupplierRequest;
import br.com.diegosantos.minierp.catalog.api.dto.SupplierResponse;
import br.com.diegosantos.minierp.catalog.application.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@Valid @RequestBody SupplierRequest request) {
        SupplierResponse response = supplierService.create(request);
        return ResponseEntity
                .created(URI.create("/api/suppliers/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> listActive() {
        return ResponseEntity.ok(supplierService.listActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request
    ) {
        return ResponseEntity.ok(supplierService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<SupplierResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.deactivate(id));
    }
}
