package com.JobPortal.JobPortal.Application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/applications")
    public class ApplicationController {

        @Autowired
        private ApplicationService appService;

        // 🔹 USER: Apply Job
        @PostMapping("/apply/{jobId}")
        public ResponseEntity<String> applyJob(
                @PathVariable Long jobId,
                Authentication authentication) {

            String email = authentication.getName();
            return ResponseEntity.ok(appService.applyJob(email, jobId));
        }

        // 🔹 USER: My Applications
        @GetMapping("/my")
        public ResponseEntity<List<ApplicationResponseDTO>> getMyApps(
                Authentication authentication) {

            String email = authentication.getName();
            return ResponseEntity.ok(appService.getMyApplications(email));
        }

        // 🔹 ADMIN: View Applicants
        @GetMapping("/admin/job/{jobId}")
        public ResponseEntity<List<ApplicationResponseDTO>> getApplicants(
                @PathVariable Long jobId) {

            return ResponseEntity.ok(appService.getApplicants(jobId));
        }

        // 🔹 ADMIN: Update Status
        @PutMapping("/admin/{appId}")
        public ResponseEntity<String> updateStatus(
                @PathVariable Long appId,
                @RequestBody UpdateStatusDTO dto) {

            return ResponseEntity.ok(appService.updateStatus(appId, dto.getStatus()));
        }
    }

