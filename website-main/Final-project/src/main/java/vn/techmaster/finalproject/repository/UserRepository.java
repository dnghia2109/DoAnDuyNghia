package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(String email);
}
