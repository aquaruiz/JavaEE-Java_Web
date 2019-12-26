package javache.http;

import java.util.*;

public class HttpSessionImpl implements HttpSession {
    private String id;
    private boolean isValid;
    private Map<String, Object> attributes;

    public HttpSessionImpl(){
        this.isValid = true;
        this.setId(UUID.randomUUID().toString());
        this.attributes = new HashMap<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    private void setId(String id){
        this.id = id;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    @Override
    public void addAttribute(String name, Object attribute) {
        this.attributes.putIfAbsent(name, attribute);
    }

    @Override
    public void invalidate() {
        this.isValid = false;
        this.attributes = new HashMap<>();
    }
}
