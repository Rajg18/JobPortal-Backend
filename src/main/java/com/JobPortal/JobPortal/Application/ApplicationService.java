package com.JobPortal.JobPortal.Application;

import com.JobPortal.JobPortal.Job.Job;
import com.JobPortal.JobPortal.Job.JobREPO;
import com.JobPortal.JobPortal.UserInfo;
import com.JobPortal.JobPortal.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {




        @Autowired
        private ApplicationRepository appRepo;

        @Autowired
        private JobREPO jobRepo;

        @Autowired
        private UserInfoRepo userRepo;

        // ✅ APPLY JOB
        public String applyJob(String email, Long jobId) {

            UserInfo user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Job job = jobRepo.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            // ❌ Prevent duplicate application
            if (appRepo.findByUserAndJob(user, job).isPresent()) {
                throw new RuntimeException("You already applied for this job");
            }

            Application app = new Application();
            app.setUser(user);
            app.setJob(job);
            app.setStatus("APPLIED");
            app.setAppliedAt(LocalDateTime.now());

            appRepo.save(app);

            return "Applied successfully";
        }

        // ✅ USER: VIEW MY APPLICATIONS
        public List<ApplicationResponseDTO> getMyApplications(String email) {

            UserInfo user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return appRepo.findByUser(user)
                    .stream()
                    .map(ApplicationResponseDTO::new)
                    .toList();
        }

        // ✅ ADMIN: VIEW APPLICANTS FOR A JOB
        public List<ApplicationResponseDTO> getApplicants(Long jobId) {

            Job job = jobRepo.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            return appRepo.findByJob(job)
                    .stream()
                    .map(ApplicationResponseDTO::new)
                    .toList();
        }

        // ✅ ADMIN: UPDATE STATUS
        public String updateStatus(Long appId, String status) {

            Application app = appRepo.findById(appId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            app.setStatus(status);
            appRepo.save(app);

            return "Status updated to " + status;
        }
    }
