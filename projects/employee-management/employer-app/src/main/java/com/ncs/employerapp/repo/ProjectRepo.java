package com.ncs.employerapp.repo;

import com.ncs.employerapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
