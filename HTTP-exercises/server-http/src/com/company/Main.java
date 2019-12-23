package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8007);

        String serverPath =
                "/media/aquariuz/Local Disk/Courses/JavaEE - Java Web/HTTP-exercises/server-http/src/resources";
//                "src" + System.lineSeparator() +
//                "resources";

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // read request
                String line;
                line = in.readLine();
                StringBuilder raw = new StringBuilder();
                raw.append("" + line);
                System.out.println(line);
                String fileName = line.split(" ")[1];
                boolean isPost = line.startsWith("POST");
                int contentLength = 0;
                while (!(line = in.readLine()).equals("")) {
                    System.out.println(line);
                    raw.append('\n' + line);
                    if (isPost) {
                        final String contentHeader = "Content-Length: ";
                        if (line.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }
                    }
                }
                StringBuilder body = new StringBuilder();
                if (isPost) {
                    int c = 0;
                    for (int i = 0; i < contentLength; i++) {
                        c = in.read();
                        body.append((char) c);
                    }
                }
                raw.append(body.toString());
                System.out.println();
                System.out.println(body);
                // send response

                if (fileName.contains("favicon")) {
                    continue;
                }

                try {
                    List<String> content = Files
                            .readAllLines(Paths
                                    .get(serverPath + fileName));
                    out.write("HTTP/1.1 200 OK\r\n");
                    out.write("Content-type: text/html\r\n");
                    out.write("\r\n");

                    out.write(String.join("", content));
                } catch (IOException e) {
                    out.write("HTTP/1.1 301 Moved Permanently\r\n");
                    out.write("Content-type: text/html\r\n");

                    out.write("Location: /pesho.html\r\n");
//                out.write("Content-Disposition: attachment; filename="+ fileName +"\r\n");
//                out.write("Content-Length: "+
//                        new File(serverPath+fileName).length() +
//                        "\r\n");
                    out.write("\r\n");
                }

                //
                // do not in.close();
                out.flush();
                out.close();
                socket.close();
                //
            } catch (Exception e) {
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
        }

    }
}
