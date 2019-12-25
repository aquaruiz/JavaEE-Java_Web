package javache.utils;

public class WebConstants {
    public static final int SOCKET_TIMEOUT_MILLISECONDS = 10000;
    public static final int NOT_FOUND = 404;
    public static final int OK = 200;
    public static final int SERVER_PORT = 8000;
    public static final String TIMEOUT_EXCEPTION_MESSAGE = "Socket TimeOut Exception No %d has been just caught.%n";
    public static final String LISTENING_MESSAGE = "Listening on port: ";
    public static final String LINE_SEPARATOR = "\r\n";
    public static final String RESOURCE_FOLDER_PATH = System.getProperty("user.dir") + "/src/resources";
    public static final String ASSETS_FOLDER_PATH = "/assets";
    public static final String PAGES_FOLDER_PATH = "/pages";
}
