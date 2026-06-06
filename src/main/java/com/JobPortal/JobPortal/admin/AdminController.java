package com.JobPortal.JobPortal.admin;

import com.JobPortal.JobPortal.Application.ApplicationRepository;
import com.JobPortal.JobPortal.Application.ApplicationStatus;
import com.JobPortal.JobPortal.Job.JobREPO;
import com.JobPortal.JobPortal.user.UserInfoRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin-only dashboard and statistics")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final UserInfoRepo           userRepo;
    private final JobREPO                jobRepo;
    private final ApplicationRepository  appRepo;

    public AdminController(UserInfoRepo userRepo,
                           JobREPO jobRepo,
                           ApplicationRepository appRepo) {
        this.userRepo = userRepo;
        this.jobRepo  = jobRepo;
        this.appRepo  = appRepo;
    }

    @Operation(
        summary     = "Get this recruiter's own statistics (ADMIN only)",
        description = "Returns job and application counts scoped to the authenticated recruiter. " +
                      "myJobs = jobs this recruiter posted. " +
                      "myApplications / accepted / rejected / pending = only for those jobs.")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponseDTO> getStats(Authentication authentication) {

        // Use the logged-in recruiter's email (from the JWT subject) to scope all counts.
        String adminEmail = authentication.getName();

        // How many jobs did THIS recruiter post?
        long myJobs = jobRepo.countByCreatedBy_Email(adminEmail);

        // How many applications landed on THIS recruiter's jobs?
        long myApplications = appRepo.countByJob_CreatedBy_Email(adminEmail);
        long accepted        = appRepo.countByJob_CreatedBy_EmailAndStatus(adminEmail, ApplicationStatus.ACCEPTED);
        long rejected        = appRepo.countByJob_CreatedBy_EmailAndStatus(adminEmail, ApplicationStatus.REJECTED);

        // Total registered users — platform-wide (useful context for the recruiter).
        long totalUsers = userRepo.count();

        return ResponseEntity.ok(
                new StatsResponseDTO(totalUsers, myJobs, myApplications, accepted, rejected));
    }
}
