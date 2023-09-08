package vn.techmaster.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    private String id;

    @NotBlank
    private String name;
    private String photo;
    private String city;

    @NaturalId
    @Email
    @NotBlank
    private String email;

    private String password;

    private LocalDateTime timeCreate;
    private LocalDateTime timeUpdate;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();
}
