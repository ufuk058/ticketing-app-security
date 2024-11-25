package com.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false,nullable = false)
    private LocalDateTime insertDateTime;
    @Column(updatable = false,nullable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;
    private Boolean isDeleted=false;


    @PrePersist
    private void onPrePersist(){
        insertDateTime=LocalDateTime.now();
        insertUserId=1L;
        lastUpdateDateTime=LocalDateTime.now();
        lastUpdateUserId=1L;
    }

    @PreUpdate
    private void onPreUpdate(){
        lastUpdateDateTime=LocalDateTime.now();
        lastUpdateUserId=1L;
    }
}
