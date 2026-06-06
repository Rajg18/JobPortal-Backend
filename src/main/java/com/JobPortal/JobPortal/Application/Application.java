package com.JobPortal.JobPortal.Application;

import com.JobPortal.JobPortal.Job.Job;
import com.JobPortal.JobPortal.user.UserInfo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"}))
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId()                         { return id; }
    public void setId(Long id)                  { this.id = id; }

    public UserInfo getUser()                   { return user; }
    public void     setUser(UserInfo user)      { this.user = user; }

    public Job  getJob()                        { return job; }
    public void setJob(Job job)                 { this.job = job; }

    public ApplicationStatus getStatus()                        { return status; }
    public void               setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getAppliedAt()                         { return appliedAt; }
    public void          setAppliedAt(LocalDateTime appliedAt)  { this.appliedAt = appliedAt; }
}
