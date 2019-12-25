package javache.http;

import javache.utils.StatusCodeMessage;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static javache.utils.WebConstants.LINE_SEPARATOR;

public class HttpResponseImpl implements HttpResponse {
    private int statusCode;
    private Map<String, String> headers;
    private String contentString;
    private byte[] content;

    public HttpResponseImpl() {
        this.headers = new LinkedHashMap<>();
        this.content = new byte[0];
        this.contentString = "";
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        String statusLine = this.getStatusLine();
        String date = "Date: Sun, 18 Oct 2012 10:36:20 GMT";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(statusLine);
        stringBuilder.append(LINE_SEPARATOR);
        stringBuilder.append(date);
        stringBuilder.append(LINE_SEPARATOR);
        stringBuilder.append(LINE_SEPARATOR);

        return stringBuilder.toString().getBytes();
    }

    private String getStatusLine() {
        String line = String.format("HTTP/1.1 %d %s", this.statusCode, StatusCodeMessage.valueOf(this.statusCode));
        return line;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content.clone();
        this.contentString += content.toString();
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }
}
