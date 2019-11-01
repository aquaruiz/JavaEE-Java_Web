package app.service;

import app.domain.entities.JobApplication;
import app.domain.models.service.JobApplicationServiceModel;
import app.repository.JobApplicationRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class JobApplicationServiceModelImpl implements JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final ModelMapper modelMapper;

    @Inject
    public JobApplicationServiceModelImpl(JobApplicationRepository jobApplicationRepository, ModelMapper modelMapper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(JobApplicationServiceModel job) {
        this.jobApplicationRepository.save(this.modelMapper.map(job, JobApplication.class));
    }

    @Override
    public List<JobApplicationServiceModel> getAll() {
        return this.jobApplicationRepository
                .findAll()
                .stream()
                .map(j -> this.modelMapper.map(j, JobApplicationServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationServiceModel getById(String id) {
        return this.modelMapper
                .map(this.jobApplicationRepository.findByI(id), JobApplicationServiceModel.class);
    }
}