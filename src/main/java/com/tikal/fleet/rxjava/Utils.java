package com.tikal.fleet.rxjava;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by chaimturkel on 6/29/16.
 */
public class Utils {

    static public HttpResponse getResponse(String url) throws RuntimeException {
        try {
            final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3 * 1000).build();
            final HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            return response;
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    static public String httpEntityToString(HttpEntity entity){
        try {
            String res = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return res;
        } catch (Exception e) {
            return "";
        }
    }
}
