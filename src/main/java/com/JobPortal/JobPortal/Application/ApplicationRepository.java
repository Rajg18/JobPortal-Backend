package com.JobPortal.JobPortal.Application;


import com.JobPortal.JobPortal.Job.Job;
import com.JobPortal.JobPortal.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface ApplicationRepository extends JpaRepository<Application, Long> {

        Optional<Application> findByUserAndJob(UserInfo user, Job job);

        List<Application> findByUser(UserInfo user);

        List<Application> findByJob(Job job);
    }
