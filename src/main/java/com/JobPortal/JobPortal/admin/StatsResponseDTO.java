package com.JobPortal.JobPortal.admin;

public class StatsResponseDTO {

    private long totalUsers;          // platform-wide — useful context
    private long myJobs;              // jobs posted by THIS recruiter
    private long myApplications;      // applications on THIS recruiter's jobs
    private long acceptedApplications;
    private long rejectedApplications;
    private long pendingApplications;

    public StatsResponseDTO(long totalUsers,
                            long myJobs,
                            long myApplications,
                            long acceptedApplications,
                            long rejectedApplications) {
        this.totalUsers            = totalUsers;
        this.myJobs                = myJobs;
        this.myApplications        = myApplications;
        this.acceptedApplications  = acceptedApplications;
        this.rejectedApplications  = rejectedApplications;
        this.pendingApplications   = myApplications - acceptedApplications - rejectedApplications;
    }

    public long getTotalUsers()            { return totalUsers; }
    public long getMyJobs()                { return myJobs; }
    public long getMyApplications()        { return myApplications; }
    public long getAcceptedApplications()  { return acceptedApplications; }
    public long getRejectedApplications()  { return rejectedApplications; }
    public long getPendingApplications()   { return pendingApplications; }
}
