package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findUserByFullNameContains(String userName);
    Boolean existsByUsername(String userName);
    Optional<User> findUserByUsername(String userName);


}
