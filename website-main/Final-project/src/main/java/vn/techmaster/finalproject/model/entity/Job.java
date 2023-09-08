package vn.techmaster.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    private String id;
    private String title;
    private String jobDescription;
    private String education;
    private String jobSkill;
    private String benefits;
    private String salary;
    private String deadline;
    private String vacancy;
    private String experience;
    private LocalDateTime timeCreate;
    private LocalDateTime timeUpdate;
    private String city;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Skill> skills;

    @Enumerated(EnumType.STRING)
    private JobType type;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL)
    private List<ApplicantJob> applicants = new ArrayList<>();


    public boolean SearchByKeywordAndCity(String search, String citySearch) {
        return (title.toLowerCase().contains(search.toLowerCase())
                &&citySearch.contains(city));
    }
    public boolean SearchByCity(String citySearch) {
        return citySearch.contains(city);
    }
    public boolean SearchByOnlyKeyword(String search) {
        return (title.toLowerCase().contains(search.toLowerCase()));
    }

}
