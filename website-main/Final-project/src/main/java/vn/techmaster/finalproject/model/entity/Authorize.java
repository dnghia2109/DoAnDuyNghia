package vn.techmaster.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Authorize {
    @Id
    private String id;
    private String userId;
    private LocalDateTime timeCreate;
    private LocalDateTime timeExp;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private boolean active;
}
