package app.repository;

import app.domain.entities.JobApplication;

import java.util.List;

public interface JobApplicationRepository {
    void save(JobApplication jobApplication);

    List<JobApplication> findAll();

    JobApplication findByI(String id);
}
