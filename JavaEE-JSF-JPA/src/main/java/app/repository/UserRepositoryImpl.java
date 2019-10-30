package app.repository;

import app.domain.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public User findByUserNameAndPassword(String username, String password) {
        this.entityManager.getTransaction().begin();
        User user = this.entityManager
                .createQuery("SELECT u FROM User AS u WHERE u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return user;
    }
}