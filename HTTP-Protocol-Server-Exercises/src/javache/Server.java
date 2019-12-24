package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

import static javache.WebConstants.*;

public class Server {
    // connection controller

    private final int port;
    private ServerSocket server;

    public Server(int serverPort) {
        this.port = serverPort;
    }

    public void run() throws IOException  {
        this.server = new ServerSocket(this.port);
        System.out.println(LISTENING_MESSAGE + this.port);

        this.server.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
        long timeouts = 0;

        while (true) {
            try (Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

                ConnectionHandler connectionHandler = new ConnectionHandler(
                        clientSocket,
                        new RequestHandler()
                );

                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException e) {
                System.out.printf(TIMEOUT_EXCEPTION_MESSAGE, ++timeouts);
            }
        }
    }
}