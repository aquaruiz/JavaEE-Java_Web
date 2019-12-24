package javache;

public enum StatusCodeMessage {
    OK (200),
    MOVED (301),
    NOT_FOUND (404),
    SERVER_ERROR (500);

    private final int code;

    StatusCodeMessage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
