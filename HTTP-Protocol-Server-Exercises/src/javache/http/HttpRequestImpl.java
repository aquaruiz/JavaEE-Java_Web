package javache.http;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static javache.utils.WebConstants.LINE_SEPARATOR;

public class HttpRequestImpl implements HttpRequest {
    private Map<String, String> headers;
    private Map<String, String> bodyParameters;
    private String method;
    private String requestUrl;


    public HttpRequestImpl(String requestContent) {
        this.headers = new LinkedHashMap<>();
        this.bodyParameters = new LinkedHashMap<>();
        this.initializeRequest(requestContent);
    }

    private void initializeRequest(String requestContent) {
        String[] arrRequest = requestContent.split(LINE_SEPARATOR);
        String[] firstRow = arrRequest[0].split("\\s+");

        String method = firstRow[0];
        this.setMethod(method);
        String requestUrl = firstRow[1];
        this.setRequestUrl(requestUrl);

        Map<String, String> headers = extractHeaders(arrRequest);
        headers.forEach(this::addHeader);

        if (method.equals("POST")) {
            Map<String, String> bodyParameters = extractBodyParameters(arrRequest);
            bodyParameters.forEach(this::addBodyParameter);
        }
    }

    private Map<String, String> extractBodyParameters(String[] arrRequest) {
        Map<String, String> bodyParams = new LinkedHashMap<>();

        // may be it's always the LAST index but not sure ?!
        String[] postBody = arrRequest[arrRequest.length - 1].split("\\&");

        for (int i = 0; i < postBody.length; i++) {
            String[] row = postBody[i].split("=");
            String key = row[0];
            String value = row[1];
            bodyParams.putIfAbsent(key, value);
        }

        return bodyParams;
    }

    private Map<String, String> extractHeaders(String[] arrRequest) {
        Map<String, String> headers = new LinkedHashMap<>();
        int index = 1;

        while (index < arrRequest.length && !arrRequest[index].equals("")) {
            String[] row = arrRequest[index].split(": ");
            String key = row[0];
            String value = row[1];
            headers.putIfAbsent(key, value);
            ++index;
        }

        return headers;
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return Collections.unmodifiableMap(this.bodyParameters);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.putIfAbsent(parameter, value);
    }

    @Override
    public boolean isResource() {
        return this.requestUrl.contains("\\.");
    }
}