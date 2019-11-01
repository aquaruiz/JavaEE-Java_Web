package app.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LogoutBean extends BaseBean {
    public void logout() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
        this.redirect("/index");
    }
}