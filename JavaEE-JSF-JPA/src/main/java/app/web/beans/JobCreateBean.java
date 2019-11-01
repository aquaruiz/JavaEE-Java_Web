package app.web.beans;

import app.domain.entities.Sector;
import app.domain.models.binding.JobCreateBindingModel;
import app.domain.models.service.JobApplicationServiceModel;
import app.service.JobApplicationService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class JobCreateBean extends BaseBean {

    private JobCreateBindingModel jobCreateBindingModel;
    private JobApplicationService jobApplicationService;
    private ModelMapper modelMapper;

    public JobCreateBean() {
    }

    @Inject
    public JobCreateBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init(){
        this.jobCreateBindingModel = new JobCreateBindingModel();
    }

    public void create(){
        JobApplicationServiceModel model = this.modelMapper.map(this.jobCreateBindingModel, JobApplicationServiceModel.class);
        Sector sector = null;

        try {
            sector = Sector.valueOf(this.jobCreateBindingModel.getSector().toUpperCase());
        } catch (Exception e) {
            this.redirect("/add-job");
        }
        model.setSector(sector);

        this.jobApplicationService.save(model);
        this.redirect("/home");
    }

    public JobCreateBindingModel getJobCreateBindingModel() {
        return jobCreateBindingModel;
    }

    public void setJobCreateBindingModel(JobCreateBindingModel jobCreateBindingModel) {
        this.jobCreateBindingModel = jobCreateBindingModel;
    }
}