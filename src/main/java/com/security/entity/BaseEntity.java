package com.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        insertDateTime=LocalDateTime.now();
        lastUpdateDateTime= LocalDateTime.now();

        if(authentication != null && !authentication.getName().equals("anonymousUser")){
           Object principal = authentication.getPrincipal();

           insertUserId= ((UserPrincipal) principal).getId();
           lastUpdateUserId= ((UserPrincipal) principal).getId();
        }


    }

    @PreUpdate
    private void onPreUpdate(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        insertDateTime=LocalDateTime.now();
        lastUpdateDateTime= LocalDateTime.now();

        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();

            insertUserId= ((UserPrincipal) principal).getId();
            lastUpdateUserId= ((UserPrincipal) principal).getId();
        }
    }
}
