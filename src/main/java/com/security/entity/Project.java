package com.security.entity;

import com.security.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name="projects")
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity{

    private String projectName;
    @Column(unique = true)
    private String projectCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User assignedManager;

    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

}

