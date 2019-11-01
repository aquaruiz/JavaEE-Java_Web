package app.web.beans;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

public abstract class BaseBean {
    protected void redirect(String path) {
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect("/views" + path + ".jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveUserToSession(String username, String id) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("username", username);
        sessionMap.put("id", id);
    }
}