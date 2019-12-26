package javache.http;

import javache.utils.StatusCodeMessage;

import java.util.*;

import static javache.utils.WebConstants.LINE_SEPARATOR;

public class HttpResponseImpl implements HttpResponse {
    private int statusCode;
    private Map<String, String> headers;
    private Map<String, HttpCookie> cookies;
    private String contentString;
    private byte[] content;

    public HttpResponseImpl() {
        this.headers = new LinkedHashMap<>();
        this.cookies = new HashMap<>();
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

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(statusLine);
        stringBuilder.append(LINE_SEPARATOR);

        this.headers.forEach((k, v) -> stringBuilder.append(String.format("%s: %s", k, v)).append(LINE_SEPARATOR));

        if (!this.cookies.isEmpty()) {
            stringBuilder.append("Set-Cookie: ");
            this.cookies.values().forEach((v) -> stringBuilder.append(v.toString()).append("; "));
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), ""); // removes last "; "
            stringBuilder.append(LINE_SEPARATOR);
        }

        stringBuilder.append(LINE_SEPARATOR);

        byte[] headersBytes = stringBuilder.toString().getBytes(); // +cookies if present
        byte[] contentBytes = this.getContent();

        if (contentBytes.length > 0) {
            byte[] outputResponse = new byte[headersBytes.length + contentBytes.length];

            System.arraycopy(headersBytes, 0, outputResponse, 0, headersBytes.length);
            System.arraycopy(contentBytes, 0, outputResponse, headersBytes.length, contentBytes.length);

            return outputResponse;
        }

        return headersBytes;
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

    @Override
    public void addCookie(String name, String value) {
        this.cookies
                .putIfAbsent(name,
                        new HttpCookieImpl(name, value)
                );
    }
}
