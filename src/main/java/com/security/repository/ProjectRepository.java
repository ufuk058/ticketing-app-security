package com.security.repository;

import com.security.entity.Project;
import com.security.entity.User;
import com.security.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByProjectCode(String projectCode);

    List<Project> findAllByAssignedManager(User manager);

    List<Project> findAllByProjectStatusIsNotAndAssignedManager(Status status, User manager);
}
