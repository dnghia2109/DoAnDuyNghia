package vn.techmaster.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
@Builder
public class Applicant {
    @Id
    private String id;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Ranking ranking;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Skill> skills;
    
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicantJob> applicantJobs = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private User user;

    public void remove(ApplicantJob applicantJob) {
        applicantJob.setApplicant(null);
        applicantJobs.remove(applicantJob);
    }

    @PreRemove
    public void preRemove() {
        applicantJobs.forEach(p -> p.setApplicant(null));
        applicantJobs.clear();
    }

}
