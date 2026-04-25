package br.com.diegosantos.minierp.catalog.infrastructure.persistence;

import br.com.diegosantos.minierp.catalog.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Long id);

    List<Product> findAllByActiveTrueOrderByNameAsc();
}
