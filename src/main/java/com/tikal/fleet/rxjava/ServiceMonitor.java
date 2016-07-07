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
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chaimturkel on 6/28/16.
 */
public class ServiceMonitor {



    Observable<HttpResponse> httpEntityObservable1 = Observable.just(Utils.getResponse("http://jsonplaceholder.typicode.com/posts/1"));


    public void getServicesStatus(){
        final JsonParser jsonParser = new JsonParser();

        Observable<String> urlRequest1 = Observable.just("http://jsonplaceholder.typicode.com/posts/1");
        Observable<String> urlRequest2 = Observable.just("http://jsonplaceholder.typicode.com/posts/2");
        Observable<String> urlRequest3 = Observable.just("http://jsonplaceholder.typicode.com/error/2");

//        urlRequest1.doOnUnsubscribe()


        Observable<String> allRequests = Observable.concat(urlRequest1, urlRequest2,urlRequest3);

        Observable<JsonElement> mapJson = allRequests
                .map(url -> Utils.getResponse(url))
                .filter(response -> response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                .map(response -> jsonParser.parse(Utils.httpEntityToString(response.getEntity())));
        Subscription subscription = mapJson
                .subscribe(
                    jsonElement -> System.out.println("element - " + jsonElement.toString()),
                    error -> System.out.println("error"),
                    () -> System.out.println("completed")
                );
//        subscription.unsubscribe();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServiceMonitor serviceMonitor = new ServiceMonitor();
        serviceMonitor.getServicesStatus();
    }

}
