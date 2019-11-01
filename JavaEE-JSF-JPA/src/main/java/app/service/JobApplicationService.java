package app.service;

import app.domain.models.service.JobApplicationServiceModel;

import java.util.List;

public interface JobApplicationService {
    void save(JobApplicationServiceModel job);

    List<JobApplicationServiceModel> getAll();

    JobApplicationServiceModel getById(String id);


}
