package javache.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javache.WebConstants.LINE_SEPARATOR;

public class HttpRequestImpl implements HttpRequest {
    private String method;
    private String requestUrl;
    HashMap<String, String> headers; // May be an ordered map?
    HashMap<String, String> bodyParameters;

    public HttpRequestImpl(String requestContent) {
        this.initializeRequest(requestContent);
    }

    // GET /hello.htm HTTP/1.1
    //User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)
    //Host: www.tutorialspoint.com
    //Accept-Language: en-us
    //Accept-Encoding: gzip, deflate
    //Connection: Keep-Alive

    private void initializeRequest(String requestContent) {
        this.headers = new HashMap<String, String>();
        this.bodyParameters = new HashMap<String, String>();

        String[] data = requestContent.split(LINE_SEPARATOR);
        String[] methodUrl = data[0].split(" ");
        this.setMethod(methodUrl[0].trim());
        this.setRequestUrl(methodUrl[1].trim());

        int index = 1;

        while (data.length > index && data[index].trim() != null){
            String[] headerData = data[index].split(": ");
            this.addHeader(headerData[0], headerData[1]);
            index++;
        }

        index++;

        while (index < data.length){
            String[] parameterData = data[index].split("&");

            Arrays.stream(parameterData).forEach(p -> {
                String[] parameter = p.split("=");
                this.addBodyParameter(parameter[0], parameter[1]);
            });

            index++;
        }
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public Map<String, String> getBodyParameters() {
        return Collections.unmodifiableMap(this.bodyParameters);
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method.trim().toUpperCase();
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl.trim();
    }

    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header.trim(), value.trim());
    }

    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.putIfAbsent(parameter.trim(), value.trim());
    }

    public boolean isResource() {
        return this.requestUrl.contains("\\.");
    }
}