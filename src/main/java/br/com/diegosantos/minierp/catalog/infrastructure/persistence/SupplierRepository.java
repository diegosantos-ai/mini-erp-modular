package br.com.diegosantos.minierp.catalog.infrastructure.persistence;

import br.com.diegosantos.minierp.catalog.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumberAndIdNot(String documentNumber, Long id);

    List<Supplier> findAllByActiveTrueOrderByNameAsc();
}
