package app.web.beans;

import app.domain.models.service.JobApplicationServiceModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class JobsListBean {
    private List<JobApplicationServiceModel> jobs;
}
