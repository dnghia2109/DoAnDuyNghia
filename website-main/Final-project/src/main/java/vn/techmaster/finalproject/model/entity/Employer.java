package vn.techmaster.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employer {
    @Id
    private String id;
    private String companyName;
    private String website;
    private String hotline;
    private CompanyType companyType;
    private String employeeSize;
    private String addressDetail;

    @OneToMany(mappedBy = "employer",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @MapsId
    @JsonIgnore
    private User user;

    @PreRemove
    public void preRemove() {
        jobs.forEach(p -> p.setEmployer(null));
        jobs.clear();
    }
}
