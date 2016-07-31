package com.tikal.fleet.concurrency;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tikal.fleet.rxjava.Utils;
import org.apache.http.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by chaimturkel on 7/8/16.
 */
public class ConcurrencyExample {

    public void run() {
        final List<String> urls = Arrays.asList("http://jsonplaceholder.typicode.com/posts/1",
                "http://jsonplaceholder.typicode.com/posts/2",
                "http://jsonplaceholder.typicode.com/error/3");
        //bad since all stream parallel use the same forkj join pool, so you will have starvation
        Stream<HttpResponse> httpResponseStream1 = urls.stream().parallel().map(url -> Utils.getResponse(url));
        final JsonParser jsonParser = new JsonParser();
        httpResponseStream1.forEach(httpResponse -> {
            JsonElement parsedData = jsonParser.parse(Utils.httpEntityToString(httpResponse.getEntity()));
            System.out.println(Thread.currentThread().getName());
            System.out.println(parsedData);
        });
    }


    public static void main(String[] args) {
        (new ConcurrencyExample()).run();
    }






}
