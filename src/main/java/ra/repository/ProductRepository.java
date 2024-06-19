package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryCategoryId(Long id);
    List<Product> findTop10ByOrderByCreatedAtDesc();
    List<Product> findProductByProductNameOrDescription(String productName, String description);
}
