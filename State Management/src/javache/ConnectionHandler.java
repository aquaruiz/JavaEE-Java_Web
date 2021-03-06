package javache;

import javache.io.Reader;
import javache.io.Writer;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private RequestHandler requestHandler;
    private Socket clientSocket;

    private InputStream clientSocketInputStream; // Request Input Stream
    private OutputStream clientSocketOutputStream; // Response Output Stream

    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.initializeConnection(clientSocket);
        this.requestHandler = requestHandler;
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientSocketInputStream = this.clientSocket.getInputStream();
            this.clientSocketOutputStream = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String requestContent = Reader.readAllLines(this.clientSocketInputStream);

            if (requestContent.length() > 1) {
                byte[] responseContent = this.requestHandler.handleRequest(requestContent);
                Writer.writeBytes(responseContent, this.clientSocketOutputStream);
            }

            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}