package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

import static javache.WebConstants.SOCKET_TIMEOUT_MILLISECONDS;

public class Server {
    // connection controller

    private ServerSocket server;
    private int port;

    public void run() throws IOException  {
        this.server = new ServerSocket(this.port);
        this.server.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
        long timeouts = 0;

        while (true) {
            try (Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException e) {
                System.out.printf("Socket TimeOut Exception No %d has been just caught.%n", ++timeouts);
            }
        }
    }
}