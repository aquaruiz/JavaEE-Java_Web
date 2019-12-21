package app.service;

import app.domain.models.service.HeroServiceModel;

import java.util.List;

public interface JobApplicationService {
    void save(HeroServiceModel job);

    List<HeroServiceModel> getAll();

    HeroServiceModel getById(String id);

    void delete(String id);
}
