package com.JobPortal.JobPortal.Profile;


import com.JobPortal.JobPortal.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface UserProfileREPO extends JpaRepository<UserProfile, Long> {

        Optional<UserProfile> findByUser(UserInfo user);
    }

