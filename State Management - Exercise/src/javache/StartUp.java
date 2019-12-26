package javache;

import java.io.IOException;

import static javache.utils.WebConstants.SERVER_PORT;

public class StartUp {
    public static void main(String[] args) throws IOException {
        Server server = new Server(SERVER_PORT);

        server.run();
    }
}
