package app.web.beans;

import app.domain.entities.Claz;
import app.domain.models.binding.HeroCreateBindingModel;
import app.domain.models.service.HeroServiceModel;
import app.service.JobApplicationService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class JobCreateBean extends BaseBean {

    private HeroCreateBindingModel heroCreateBindingModel;
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
        this.heroCreateBindingModel = new HeroCreateBindingModel();
    }

    public void create(){
        HeroServiceModel model = this.modelMapper.map(this.heroCreateBindingModel, HeroServiceModel.class);
        Claz claz = null;

        try {
            claz = Claz.valueOf(this.heroCreateBindingModel.getSector().toUpperCase());
        } catch (Exception e) {
            this.redirect("/add-job");
        }
        model.setClaz(claz);

        this.jobApplicationService.save(model);
        this.redirect("/home");
    }

    public HeroCreateBindingModel getHeroCreateBindingModel() {
        return heroCreateBindingModel;
    }

    public void setHeroCreateBindingModel(HeroCreateBindingModel heroCreateBindingModel) {
        this.heroCreateBindingModel = heroCreateBindingModel;
    }
}