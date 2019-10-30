package app.service;

import app.domain.entities.User;
import app.domain.models.service.UserServiceModel;

public interface UserService {
    void save (UserServiceModel user);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);
}
