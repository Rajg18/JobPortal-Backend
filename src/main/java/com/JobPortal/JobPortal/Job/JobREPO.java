package com.JobPortal.JobPortal.Job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface JobREPO extends JpaRepository<Job, Long> {
    }

