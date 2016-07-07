package com.tikal.fleet.rxjava;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

import java.io.IOException;

/**
 * Created by chaimturkel on 6/29/16.
 */
public class ServiceMonitorThreaded {



    public void getServicesStatus(){
        final JsonParser jsonParser = new JsonParser();

        Observable<String> urlRequest1 = Observable.just("http://jsonplaceholder.typicode.com/posts/1");
        Observable<String> urlRequest2 = Observable.just("http://jsonplaceholder.typicode.com/posts/2");
        Observable<String> urlRequest3 = Observable.just("http://jsonplaceholder.typicode.com/error/2");

        Observable<String> allRequests = Observable.concat(urlRequest1, urlRequest2,urlRequest3);
        allRequests.flatMap(request -> Observable.just(request)
                .map(url -> Utils.getResponse(url))
                .filter(response -> response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                .map(response -> jsonParser.parse(Utils.httpEntityToString(response.getEntity())))
                .subscribeOn(Schedulers.computation())
        ).toList()
        .subscribe(
                jsonElementList -> {
                    for (JsonElement jsonElement : jsonElementList) {
                        System.out.println("element - " + jsonElement.toString());
                    }
                },
                error -> System.out.println("error"),
                () -> System.out.println("completed"));


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServiceMonitorThreaded serviceMonitor = new ServiceMonitorThreaded();
        serviceMonitor.getServicesStatus();
    }

}

