package javache;

import javache.http.HttpRequest;
import javache.http.HttpRequestImpl;
import javache.http.HttpResponse;
import javache.http.HttpResponseImpl;

import java.time.LocalDate;

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
        this.httpResponse.setStatusCode(404);
        this.httpResponse.addHeader("Date", String.valueOf(LocalDate.now()));
    }

    private void addMimeType() {
        String type = this.httpRequest.getRequestUrl().substring(this.httpRequest.getRequestUrl().indexOf(".") + 1);

        if (type.equals("html")) {
            this.httpResponse.addHeader("Content-Type", "multipart");
        } else if (type.equals("jpg") || type.equals("jpeg") || type.equals("png")) {
            this.httpResponse.addHeader("Content-Type", "image/png");
        } else {
            this.httpResponse.addHeader("Content-Type", "text/html");
        }
    }
}