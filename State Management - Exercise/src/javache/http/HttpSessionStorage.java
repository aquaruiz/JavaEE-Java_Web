package javache.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpSessionStorage {
    private Map<String, HttpSession> allSessions;

    public HttpSessionStorage() {
        this.allSessions = new HashMap<>();
    }

    public HttpSession getById(String sessionId){
        if (!this.allSessions.containsKey(sessionId)){
            return null;
        }

        return this.allSessions.get(sessionId);
    }

    public void addSession(HttpSession session) {
        this.allSessions.putIfAbsent(session.getId(), session);
    }

    public void refreshSessions() {
        List<String> idsToRemove = new ArrayList<>();

        for (HttpSession httpSession : allSessions.values()) {
            if (!httpSession.isValid()){
                idsToRemove.add(httpSession.getId());
            }
        }

        for (String id : idsToRemove) {
            this.allSessions.remove(id);
        }
    }
}
