package app.web.beans;

import app.domain.models.binding.UserLoginBindingModel;
import app.domain.models.service.UserServiceModel;
import app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBean extends BaseBean {
    private UserService userService;
    private UserLoginBindingModel user;

    public LoginBean() {
    }

    @Inject
    public LoginBean(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        this.user = new UserLoginBindingModel();
    }

    public void login() {
        UserServiceModel user = this.userService
                .findUserByUsernameAndPassword(this.user.getUsername(), DigestUtils.sha3_256Hex(this.user.getPassword()));

        if (user == null) {
            this.redirect("/login");
        }

        this.saveUserToSession(user.getUsername(), user.getUsername());

        this.redirect("/home");
    }

    public UserLoginBindingModel getUser() {
        return user;
    }

    public void setUser(UserLoginBindingModel user) {
        this.user = user;
    }
}