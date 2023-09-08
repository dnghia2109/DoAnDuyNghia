package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File,String> {
}
