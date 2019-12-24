package javache.http;

import java.util.*;

public class HttpResponseImpl implements HttpResponse {

    private HashMap<String, String> headers;
    private int statusCode;
    private byte[] content;

    public HttpResponseImpl() {
        this.setContent(new byte[0]);
        this.headers = new HashMap<>();
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public byte[] getContent() {
        return this.content.clone();
    }

    public byte[] getBytes() {
        byte[] bytes = new byte[2 + this.content.length + this.headers.size()];
        bytes[0] = Byte.parseByte(String.format("%s %d\r\n", "HTTP/1.1", this.statusCode));

        StringBuilder headerBytes = new StringBuilder();

        for (Map.Entry<String, String> kvp : this.headers.entrySet()) {
            headerBytes
                    .append(
                            String.format("%s: %s",
                                    kvp.getKey(),
                                    kvp.getValue()
                            ))
                    .append(System.lineSeparator());
        }

        headerBytes
                .append(String.format("Date: %s", new Date()))
                .append(System.lineSeparator());
        headerBytes
                .append("Server: MyJavache/--1.0.0")
                .append(System.lineSeparator());
        headerBytes
                .append(System.lineSeparator());

        System
                .arraycopy(headerBytes.toString().getBytes(),
                        0,
                        bytes,
                        1,
                        headerBytes.toString().getBytes().length
                );

        System.arraycopy(this.content,
                0, bytes,
                headerBytes.toString().getBytes().length + 1,
                this.content.length
        );

        return bytes;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header.trim(), value.trim());
    }
}