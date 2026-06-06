package com.JobPortal.JobPortal.Job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobREPO extends JpaRepository<Job, Long> {

    // Count only the jobs posted by a specific recruiter (by their email).
    long countByCreatedBy_Email(String adminEmail);

    /**
     * Dynamic search across existing Job fields.
     * Every filter is optional — passing null skips that condition.
     *
     * Filters available (all from the existing Job entity):
     *   location    → partial, case-insensitive match on job.location
     *   techStack   → partial, case-insensitive match on job.techStack
     *   companyName → partial, case-insensitive match on job.companyName
     *   experience  → returns jobs where experienceRequired <= given value
     *                 (i.e. "show me jobs I qualify for with X years of experience")
     */
    @Query("SELECT j FROM Job j WHERE " +
           "(:location    IS NULL OR LOWER(j.location)    LIKE LOWER(CONCAT('%', :location,    '%'))) AND " +
           "(:techStack   IS NULL OR LOWER(j.techStack)   LIKE LOWER(CONCAT('%', :techStack,   '%'))) AND " +
           "(:companyName IS NULL OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))) AND " +
           "(:experience  IS NULL OR j.experienceRequired <= :experience)                              AND " +
           "(:postedBy    IS NULL OR j.createdBy.email    = :postedBy)")
    Page<Job> searchJobs(@Param("location")    String  location,
                         @Param("techStack")   String  techStack,
                         @Param("companyName") String  companyName,
                         @Param("experience")  Integer experience,
                         @Param("postedBy")    String  postedBy,
                         Pageable pageable);
}
