package vn.techmaster.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.Job;


@Repository
public interface JobRepository extends JpaRepository<Job,String>  {
    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM job \n " +
            "WHERE state = 'ACTIVE'\n" +
            "AND LOWER(title) LIKE %?1%  \n"+
            "AND city  = ?2 \n"+
            "ORDER BY time_create DESC")
    Page<Job> findAllByAll (String keyword,String city,Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM job \n " +
            "WHERE state = 'ACTIVE'\n" +
            "AND LOWER(title) LIKE %?1% \n"+
            "ORDER BY time_create DESC")
    Page<Job> findAllByKey (String keyword,Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM job \n " +
            "WHERE state = 'ACTIVE'\n" +
            "AND city  = ?1 \n"+
            "ORDER BY time_create DESC")
    Page<Job> findAllByCity (String city,Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM job \n " +
            "WHERE state = 'ACTIVE'\n"+
            "ORDER BY time_create DESC")
    Page<Job> findAllPaging (Pageable pageable);

}
