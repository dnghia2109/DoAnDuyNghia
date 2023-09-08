package vn.techmaster.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicantJob {
    @Id
    private String id;
    private Boolean love;
    private String cv_path;

    @Enumerated(EnumType.STRING)
    private ApplyState applyState;
    
    private LocalDateTime timeCreate;
    private LocalDateTime timeUpdate;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "job_id")
   @JsonIgnore
   private Job job;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "applicant_id")
   @JsonIgnore
   private Applicant applicant;
}
