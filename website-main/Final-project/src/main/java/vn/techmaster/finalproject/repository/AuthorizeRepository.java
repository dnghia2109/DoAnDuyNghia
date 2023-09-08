package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.Authorize;

@Repository
public interface AuthorizeRepository extends JpaRepository<Authorize,String> {
}
