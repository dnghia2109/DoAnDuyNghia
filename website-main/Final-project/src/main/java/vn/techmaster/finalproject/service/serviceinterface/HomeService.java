package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.model.entity.Job;
import vn.techmaster.finalproject.security.UserDetailCustom;

import java.util.Collection;

@Service
public interface HomeService {
    public abstract Page<Job> findAllByTitleContains(String keyword,String city,Pageable pageable);

    public abstract boolean checkHide( String job_id);

    public abstract Collection<Job> SearchByKeyword(String search, String city);

    public abstract Collection<Job> ListAll();

    public abstract Collection<Job> ListAllPage();


}
