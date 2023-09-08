package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.techmaster.finalproject.model.entity.Job;
import vn.techmaster.finalproject.model.entity.Skill;

import java.util.List;

public interface AccountService {
    public abstract void registerApplicant(String firstName, String lastName, String phoneNumber, String email, String password, String city, List<Skill> skills);

    public abstract String validateUser(String token);

    public abstract void forgotPass(String email);

    public abstract void changePass(String token,String password);

    public abstract void changePassManual(String oldPass, String NewPass , String userId);

    public abstract void registerCompany(String firstName , String lastName , String companyName, String website , String city, String phoneNumber , String email , String password );

}
