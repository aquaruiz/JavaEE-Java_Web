package javache;

import javache.http.HttpRequest;
import javache.http.HttpRequestImpl;
import javache.http.HttpResponse;
import javache.http.HttpResponseImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static javache.utils.WebConstants.*;

public class RequestHandler {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    protected RequestHandler() {
    }

    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        this.constructHttpResponse();
        return this.httpResponse.getBytes();
    }

    private void constructHttpResponse() {
        this.httpResponse.setStatusCode(318);
        this.httpResponse.addHeader("Date", String.valueOf(LocalDateTime.now()));
        this.httpResponse.addHeader("Server-version", "Javache -5.0.2");

        String requestUrl = "";

        if (this.httpRequest.isResource()) {
            if (!this.httpRequest.getRequestUrl().contains("htm")) {
                requestUrl = RESOURCE_FOLDER_PATH +
                        ASSETS_FOLDER_PATH +
                        this.httpRequest.getRequestUrl();

                String[] typeArr = this.httpRequest.getRequestUrl().split("\\.");
                String type = typeArr[typeArr.length - 1];
                this.addMimeType(type);
            }
        } else {
            requestUrl = RESOURCE_FOLDER_PATH +
                    PAGES_FOLDER_PATH +
                    this.httpRequest.getRequestUrl();
            requestUrl += ".html";

            this.addMimeType("html");
        }

        byte[] fileBytes = new byte[0];

        try {
            this.httpResponse.setStatusCode(200);
            fileBytes = Files.readAllBytes(Paths.get(requestUrl));
        } catch (IOException e) {
            this.httpResponse.setStatusCode(404);
            this.addMimeType("html");
            try {
                fileBytes = Files.readAllBytes(Paths.get(
                        RESOURCE_FOLDER_PATH +
                                PAGES_FOLDER_PATH +
                                "/not-found.html"
                ));
            } catch (IOException ex) {
                this.httpResponse.setStatusCode(500);
                ex.printStackTrace();
            }
        }

        if (fileBytes.length > 0) {
            this.httpResponse.setContent(fileBytes);
        }

//        if (this.httpRequest.getMethod().equals("GET")){
//             // TODO
//        } else if (this.httpRequest.getMethod().equals("POST")) {
//             // TODO
//        }
    }

    private void addMimeType(String type) {
        if (type.equals("file")) {
            this.httpResponse.addHeader("Content-Type", "multipart");
        } else if (type.equals("jpg")
                || type.equals("jpeg")
                || type.equals("png")) {
            this.httpResponse.addHeader("Content-Type", "image/png");
        } else if (type.equals("html")
                || type.equals("htm")) {
            this.httpResponse.addHeader("Content-Type", "text/html");
        } else {
            this.httpResponse.addHeader("Content-Type", "text/plain");
        }
    }
}