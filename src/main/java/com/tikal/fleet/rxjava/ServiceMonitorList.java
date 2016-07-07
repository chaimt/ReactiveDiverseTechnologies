package com.tikal.fleet.rxjava;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import rx.Observable;

/**
 * Created by chaimturkel on 6/29/16.
 */
public class ServiceMonitorList {


    public void getServicesStatus(){
        final JsonParser jsonParser = new JsonParser();


//        Observable<HttpResponse> httpEntityObservable1 = Observable.fromCallable(
//                () -> Utils.getResponse("http://jsonplaceholder.typicode.com/posts/1"));

        Observable<String> urlRequest1 = Observable.just("http://jsonplaceholder.typicode.com/posts/1");
        Observable<String> urlRequest2 = Observable.just("http://jsonplaceholder.typicode.com/posts/2");
        Observable<String> urlRequest3 = Observable.just("http://jsonplaceholder.typicode.com/error/2");

        Observable<String> allRequests = Observable.concat(urlRequest1, urlRequest2,urlRequest3);
//                .subscribeOn(Schedulers.computation()) //thread that the observable will run
//                .observeOn(Schedulers.io()); //thread that transformations and onext will run (gui app - ui thread [android])

        Observable<JsonElement> mapJson = allRequests
                .map(url -> Utils.getResponse(url))
                .filter(response -> response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                .map(response -> jsonParser.parse(Utils.httpEntityToString(response.getEntity())));
        mapJson.toList().subscribe(
                jsonElementList -> {
                    for (JsonElement jsonElement : jsonElementList) {
                        System.out.println("element - " + jsonElement.toString());
                    }
                },
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
        ServiceMonitorList serviceMonitor = new ServiceMonitorList();
        serviceMonitor.getServicesStatus();
    }

}

