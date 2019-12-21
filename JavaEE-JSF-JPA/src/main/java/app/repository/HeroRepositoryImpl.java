package app.repository;

import app.domain.entities.Hero;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class JobApplicationRepositoryImpl implements JobApplicationRepository {
    private final EntityManager entityManager;

    @Inject
    public JobApplicationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Hero hero) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(hero);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<Hero> findAll() {
        this.entityManager.getTransaction().begin();
        List<Hero> jobs = this.entityManager
                .createQuery("SELECT j FROM Hero j", Hero.class)
                .getResultList();
        this.entityManager.getTransaction().commit();
        return jobs;
    }

    @Override
    public Hero findByI(String id) {
        this.entityManager.getTransaction().begin();
        Hero job = this.entityManager
                .createQuery("SELECT j FROM Hero j WHERE j.id LIKE :id", Hero.class)
                .setParameter("id", id)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return job;
    }

    @Override
    public void delete(String id) {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("DELETE FROM Hero j WHERE j.id = :id")
                .setParameter("id", id);
        this.entityManager.getTransaction().commit();
    }
}