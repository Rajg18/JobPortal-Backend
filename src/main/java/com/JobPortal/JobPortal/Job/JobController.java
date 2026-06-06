package com.JobPortal.JobPortal.Job;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Job listing and management")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // ── ADMIN: Create job ─────────────────────────────────────────────────────

    @Operation(summary = "Create a new job posting (ADMIN only)")
    @PostMapping("/admin")
    public ResponseEntity<JobResponseDTO> createJob(@Valid @RequestBody JobRequestDTO dto,
                                                    Authentication authentication) {
        return ResponseEntity.ok(jobService.createJob(authentication.getName(), dto));
    }

    // ── USER/ADMIN: Search & list jobs (all filters optional) ─────────────────

    @Operation(summary = "Search jobs — all query params are optional and combinable",
               description = "Filter by location, techStack, companyName (partial match, case-insensitive). " +
                             "Filter by experience = max years required (shows jobs you qualify for). " +
                             "Supports pagination via page & size.")
    @GetMapping
    public ResponseEntity<Page<JobResponseDTO>> searchJobs(
            @RequestParam(required = false) String  location,
            @RequestParam(required = false) String  techStack,
            @RequestParam(required = false) String  companyName,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) String  postedBy,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(
                jobService.searchJobs(location, techStack, companyName, experience, postedBy, pageable));
    }

    // ── USER/ADMIN: Get single job ────────────────────────────────────────────

    @Operation(summary = "Get details of a specific job by ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    // ── ADMIN: Delete job ─────────────────────────────────────────────────────

    @Operation(summary = "Delete a job posting (ADMIN only)")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}
