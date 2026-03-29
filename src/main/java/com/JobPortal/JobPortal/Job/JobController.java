package com.JobPortal.JobPortal.Job;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/jobs")
public class JobController {



        @Autowired
        private JobService jobService;

        // 🔹 ADMIN: Create Job
        @PostMapping("/admin")
        public ResponseEntity<JobResponseDTO> createJob(
                @RequestBody @Valid JobRequestDTO dto,
                Authentication authentication) {

            String adminEmail = authentication.getName();

            return ResponseEntity.ok(
                    jobService.createJob(adminEmail, dto)
            );
        }

        // 🔹 USER: View All Jobs
        @GetMapping
        public ResponseEntity<List<JobResponseDTO>> getAllJobs() {
            return ResponseEntity.ok(jobService.getAllJobs());
        }

        // 🔹 USER: View Job Details
        @GetMapping("/{id}")
        public ResponseEntity<JobResponseDTO> getJob(@PathVariable Long id) {
            return ResponseEntity.ok(jobService.getJobById(id));
        }

        // 🔹 ADMIN: Delete Job
        @DeleteMapping("/admin/{id}")
        public ResponseEntity<String> deleteJob(@PathVariable Long id) {
            jobService.deleteJob(id);
            return ResponseEntity.ok("Job deleted successfully");
        }
    }

