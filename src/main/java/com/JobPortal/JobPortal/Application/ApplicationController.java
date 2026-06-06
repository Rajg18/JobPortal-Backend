package com.JobPortal.JobPortal.Application;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService appService;

    public ApplicationController(ApplicationService appService) {
        this.appService = appService;
    }

    // ── USER: Apply to a job ──────────────────────────────────────────────────

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyJob(@PathVariable Long jobId,
                                           Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(appService.applyJob(email, jobId));
    }

    // ── USER: View own applications ───────────────────────────────────────────

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApps(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(appService.getMyApplications(email));
    }

    // ── ADMIN: View all applicants for a job ──────────────────────────────────

    @GetMapping("/admin/job/{jobId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicants(@PathVariable Long jobId) {
        return ResponseEntity.ok(appService.getApplicants(jobId));
    }

    // ── ADMIN: Update application status ─────────────────────────────────────

    @PutMapping("/admin/{appId}")
    public ResponseEntity<String> updateStatus(@PathVariable Long appId,
                                               @Valid @RequestBody UpdateStatusDTO dto) {
        return ResponseEntity.ok(appService.updateStatus(appId, dto.getStatus()));
    }
}
