package com.JobPortal.JobPortal.Job;

import com.JobPortal.JobPortal.user.UserInfo;
import com.JobPortal.JobPortal.user.UserInfoRepo;
import com.JobPortal.JobPortal.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobService {

    private final JobREPO jobRepo;
    private final UserInfoRepo userRepo;

    public JobService(JobREPO jobRepo, UserInfoRepo userRepo) {
        this.jobRepo  = jobRepo;
        this.userRepo = userRepo;
    }

    // ── Create job (ADMIN) ────────────────────────────────────────────────────

    public JobResponseDTO createJob(String adminEmail, JobRequestDTO dto) {
        UserInfo admin = userRepo.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found: " + adminEmail));

        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setTechStack(dto.getTechStack());
        job.setExperienceRequired(dto.getExperienceRequired());
        job.setCompanyName(dto.getCompanyName());
        job.setCreatedAt(LocalDateTime.now());
        job.setCreatedBy(admin);

        return new JobResponseDTO(jobRepo.save(job));
    }

    // ── Search / list jobs — all filters optional, all paginated ─────────────

    public Page<JobResponseDTO> searchJobs(String location,
                                           String techStack,
                                           String companyName,
                                           Integer experience,
                                           String  postedBy,
                                           Pageable pageable) {
        return jobRepo.searchJobs(location, techStack, companyName, experience, postedBy, pageable)
                      .map(JobResponseDTO::new);
    }

    // ── Get single job ────────────────────────────────────────────────────────

    public JobResponseDTO getJobById(Long id) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        return new JobResponseDTO(job);
    }

    // ── Delete job (ADMIN) ────────────────────────────────────────────────────

    public void deleteJob(Long id) {
        if (!jobRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job not found with id: " + id);
        }
        jobRepo.deleteById(id);
    }
}
