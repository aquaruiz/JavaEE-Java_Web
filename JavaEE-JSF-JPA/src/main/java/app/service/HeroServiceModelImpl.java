package app.service;

import app.domain.entities.Hero;
import app.domain.models.service.HeroServiceModel;
import app.repository.HeroRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class JobApplicationServiceModelImpl implements JobApplicationService {
    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;

    @Inject
    public JobApplicationServiceModelImpl(HeroRepository heroRepository, ModelMapper modelMapper) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(HeroServiceModel job) {
        this.heroRepository.save(this.modelMapper.map(job, Hero.class));
    }

    @Override
    public List<HeroServiceModel> getAll() {
        return this.heroRepository
                .findAll()
                .stream()
                .map(j -> this.modelMapper.map(j, HeroServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public HeroServiceModel getById(String id) {
        return this.modelMapper
                .map(this.heroRepository.findByI(id), HeroServiceModel.class);
    }

    @Override
    public void delete(String id) {
        this.heroRepository.delete(id);
    }
}