package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Product;
import ra.model.entity.ShoppingCart;
import ra.model.entity.User;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    List<ShoppingCart> findAllByUser(User user);
    ShoppingCart findShoppingCartByProductAndUser(Product product, User user);
}
