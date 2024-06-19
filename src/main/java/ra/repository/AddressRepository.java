package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Address;
import ra.model.entity.User;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUser(User user);
}
