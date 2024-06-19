package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Product;
import ra.model.entity.User;
import ra.model.entity.WishList;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
Optional<WishList> findByUserAndProduct(User user, Product product);
List<WishList> findAllByUser(User user);
}
