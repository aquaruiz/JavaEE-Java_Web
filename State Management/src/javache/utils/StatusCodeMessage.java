package javache.utils;

import java.util.HashMap;
import java.util.Map;

public enum StatusCodeMessage {
    OK (200),
    CREATED (201),
    MULTIPLE_CHOICES (300),
    MOVED_PERMANENTLY (301),
    FOUND (302),
    MOVED_TEMPORARILY (303),
    NOT_MODIFIED (304),
    TEMPORARY_REDIRECT (307),
    I_AM_A_TEAPOT (318),
    BAD_REQUEST (400),
    UNAUTHORIZED (401),
    FORBIDDEN (403),
    NOT_FOUND (404),
    INTERNAL_SERVER_ERROR (500);

    private final int code;
    private static Map map = new HashMap<>();

    StatusCodeMessage(int code) {
        this.code = code;
    }

    static {
        for (StatusCodeMessage statusCode : StatusCodeMessage.values()) {
            map.put(statusCode.code, statusCode);
        }
    }

    public int getCode() {
        return code;
    }

    public static String valueOf(int statusCode) {
        return  map.get(statusCode).toString().contains("_") ?
                map.get(statusCode).toString().replace("_", " ") :
                map.get(statusCode).toString();
    }
}
