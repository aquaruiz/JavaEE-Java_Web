package javache;

import javache.http.*;
import javache.utils.StatusCodeMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javache.utils.WebConstants.*;

public class RequestHandler {
    private static final String HTML_EXTENSION_AND_SEPARATOR = ".html";
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private HttpSessionStorage sessionStorage;

    protected RequestHandler(HttpSessionStorage httpSessionStorage) {
        this.sessionStorage = httpSessionStorage;
    }

    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        if (this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        }

        this.sessionStorage.refreshSessions();

//        this.constructHttpResponse();
        return result;
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

    private byte[] ok(byte[] content) {
        this.httpResponse.setStatusCode(StatusCodeMessage.OK.getCode());
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] redirect(byte[] content, String location) {
        this.httpResponse.setStatusCode(StatusCodeMessage.MOVED_TEMPORARILY.getCode());
        this.httpResponse.addHeader("Location", location);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] notFound(byte[] content) {
        this.httpResponse.setStatusCode(StatusCodeMessage.NOT_FOUND.getCode());
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] internalServerError(byte[] content) {
        this.httpResponse.setStatusCode(StatusCodeMessage.INTERNAL_SERVER_ERROR.getCode());
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] processResourceRequest() {
        String assetName = this.httpRequest.getRequestUrl().substring(1);
        String assetPath = RESOURCE_FOLDER_PATH +
                ASSETS_FOLDER_PATH + File.separator +
                assetName;

        File file = new File(assetPath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Asset been not found").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(assetPath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong. :(").getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));
        this.httpResponse.addHeader("Content-Length", result.length + "");
        this.httpResponse.addHeader("Content-Disposition", "inline");
//        this.httpResponse.addHeader("Content-Disposition", "attachment");

        return this.ok(result);
    }

    private String getMimeType(File file) {
        String fileName = file.getName();

        if (fileName.endsWith("css")) {
            return "text/css";
        } else if (fileName.endsWith("html")) {
            return "text/html";
        } else if (fileName.endsWith("jpg")
                || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        } else if (fileName.endsWith("json")) {
            return "application/json";
        } else if (fileName.endsWith("ico")) {
            return "image/webp";
        }

        return "text/plain";
    }

    private byte[] processPageRequest(String page){
        String pagePath = RESOURCE_FOLDER_PATH +
                PAGES_FOLDER_PATH + File.separator +
                page.substring(1) +
                HTML_EXTENSION_AND_SEPARATOR;

        File file = new File(pagePath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(pagePath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong. :(").getBytes());
        }


        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] processGetRequest() {
        if (this.httpRequest.getRequestUrl().equals("/index")
                || this.httpRequest.getRequestUrl().equals("/")) {

            return this.processPageRequest("/index");
        } else if (this.httpRequest.getRequestUrl().equals("/login")) {

            HttpSession session = new HttpSessionImpl();
            session.addAttribute("username", "az");

            this.sessionStorage.addSession(session);

            this.httpResponse.addCookie("Session-id", session.getId());
            return this.processPageRequest(this.httpRequest.getRequestUrl());
        } else if (this.httpRequest.getRequestUrl().equals("/logout")) {

            if (!this.httpRequest.getCookies().containsKey("Session-id")) {
                return this.redirect("You have to log in to access this route".getBytes(), "/");
            }

            String sessionId = this.httpRequest.getCookies().get("Session-id").getValue();
            this.sessionStorage.getById(sessionId).invalidate();

            this.httpResponse.addCookie("Session-id", "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT");
            return this.ok("Successfully expired!".getBytes());
        } else if (this.httpRequest.getRequestUrl().contains("forbidden")) {

            if (!this.httpRequest.getCookies().containsKey("Session-id")){
                return this.redirect("You have to log in to access this route".getBytes(), "/");
            }

            String sessionId = this.httpRequest.getCookies().get("Session-id").getValue();
            HttpSession session = this.sessionStorage.getById(sessionId);
            String username = session.getAttributes().get("username").toString();

            return this.ok((username + ", YOU are LOGGED in").getBytes());
        }

        return this.processResourceRequest();
    }
}