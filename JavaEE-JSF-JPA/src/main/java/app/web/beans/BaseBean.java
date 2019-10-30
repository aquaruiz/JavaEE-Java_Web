package app.web.beans;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

public abstract class BaseBean {
    protected void redirect(String path) throws IOException {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect("faces/views" + path + ".jsf");
    }

    protected void saveUserToSession(String username, String id) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("username", username);
        sessionMap.put("id", id);
    }
}
