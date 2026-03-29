package com.JobPortal.JobPortal.Job;

import com.JobPortal.JobPortal.UserInfo;
import com.JobPortal.JobPortal.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {



        @Autowired
        private JobREPO jobRepo;

        @Autowired
        private UserInfoRepo userRepo;

        public JobResponseDTO createJob(String adminEmail, JobRequestDTO dto) {

            UserInfo admin = userRepo.findByEmail(adminEmail)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            Job job = new Job();
            job.setTitle(dto.getTitle());
            job.setDescription(dto.getDescription());
            job.setLocation(dto.getLocation());
            job.setTechStack(dto.getTechStack());
            job.setExperienceRequired(dto.getExperienceRequired());
            job.setCompanyName(dto.getCompanyName());
            job.setCreatedAt(LocalDateTime.now());
            job.setCreatedBy(admin);

            jobRepo.save(job);

            return new JobResponseDTO(job);
        }

        public List<JobResponseDTO> getAllJobs() {
            return jobRepo.findAll()
                    .stream()
                    .map(JobResponseDTO::new)
                    .toList();
        }

        public JobResponseDTO getJobById(Long id) {
            Job job = jobRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            return new JobResponseDTO(job);
        }

        public void deleteJob(Long id) {
            jobRepo.deleteById(id);
        }
    }

