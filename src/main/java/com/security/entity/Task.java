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
@Table(name = "tasks")
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private User assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;
    @Enumerated(EnumType.STRING)
    private Status taskStatus;
}
