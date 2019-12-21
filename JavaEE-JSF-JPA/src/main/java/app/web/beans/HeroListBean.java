package app.web.beans;

import app.domain.models.view.HeroViewModel;
import app.service.HeroService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class JobsListBean {
    private List<HeroViewModel> jobs;
    private HeroService heroService;
    private ModelMapper modelMapper;

    public JobsListBean() {
    }

    @Inject
    public JobsListBean(HeroService heroService, ModelMapper modelMapper) {
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        this.setJobs(this.heroService.getAll().stream()
                .map(j -> this.modelMapper.map(j, HeroViewModel.class))
                .collect(Collectors.toList()));

        this.getJobs().forEach(j -> j.setSector(j.getSector().toLowerCase()));
    }

    public List<HeroViewModel> getJobs() {
        return jobs;
    }

    public void setJobs(List<HeroViewModel> jobs) {
        this.jobs = jobs;
    }
}