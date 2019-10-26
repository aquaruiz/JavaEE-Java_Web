package javache.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class HttpRequestImpl implements HttpRequest {
    private String method;
    private String requestUrl;
    HashMap<String, String> headers;
    HashMap<String, String> bodyParameters;

    public HttpRequestImpl(String requestContent) {
        this.initializeRequest(requestContent);
    }

    private void initializeRequest(String requestContent) {
        this.headers = new HashMap<String, String>();
        this.bodyParameters = new HashMap<String, String>();

        String[] data = requestContent.split(System.lineSeparator());
        String[] methodUrl = data[0].split(" ");
        this.setMethod(methodUrl[0].trim());
        this.setRequestUrl(methodUrl[1].trim());

        int index = 1;
        while (data[index].trim() != null){
            String[] headerData = data[index].split(": ");
            this.addHeader(headerData[0], headerData[1]);
            index++;
        }

        index++;
        while (index <= data.length){
            String[] parameterData = data[index].split("&");

            Arrays.stream(parameterData).forEach(p -> {
                String[] parameter = p.split("=");
                this.addBodyParameter(parameter[0], parameter[1]);
            });

            index++;
        }
    }
    public HashMap<String, String> getHeaders() {
        return (HashMap<String, String>) this.headers.clone();

    }

    public HashMap<String, String> getBodyParameters() {
        return (HashMap<String, String>) this.bodyParameters.clone();
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = "//" + requestUrl;

    }

    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }

    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.putIfAbsent(parameter, value);
    }

    public boolean isResource() {
        return this.requestUrl.contains("\\.");
    }
}