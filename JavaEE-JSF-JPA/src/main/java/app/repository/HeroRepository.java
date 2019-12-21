package app.repository;

import app.domain.entities.Hero;

import java.util.List;

public interface JobApplicationRepository {
    void save(Hero hero);

    List<Hero> findAll();

    Hero findByI(String id);

    void delete(String id);
}
