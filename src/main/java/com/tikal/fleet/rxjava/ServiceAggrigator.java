package com.tikal.fleet.rxjava;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by chaimturkel on 6/29/16.
 */
public class ServiceAggrigator {

    private class RequestWrapper<T> {
        private String header;
        private T payload;

        public RequestWrapper(String header, T payload) {
            this.header = header;
            this.payload = payload;
        }
    }


    public RequestWrapper<HttpResponse> mapRequestToResponse(RequestWrapper<String> request) {
        return new RequestWrapper<HttpResponse>(request.header, Utils.getResponse(request.payload));
    }

    public RequestWrapper<JsonElement> mapResponseToJson(JsonParser jsonParser, RequestWrapper<HttpResponse> response) {
        return new RequestWrapper<JsonElement>(response.header, jsonParser.parse(Utils.httpEntityToString(response.payload.getEntity())));
    }

    public void getServicesStatus() {
        final JsonParser jsonParser = new JsonParser();

        Observable<RequestWrapper<String>> urlRequest1 = Observable.just(new RequestWrapper<String>("gps", "http://gpsservice/seg1/134"));
        Observable<RequestWrapper<String>> urlRequest2 = Observable.just(new RequestWrapper<String>("trucksavailability", "http://vehiclemanagement/availability"));
        Observable<RequestWrapper<String>> urlRequest3 = Observable.just(new RequestWrapper<String>("truckshealth", "http://Analyticsservice/trucks/health"));

        Observable<RequestWrapper<String>> allRequests = Observable.concat(urlRequest1, urlRequest2, urlRequest3);
        allRequests.flatMap(request -> Observable.just(request)
                .observeOn(Schedulers.io())
                .map(this::mapRequestToResponse)
                .retry(2)
                .filter(response -> response.payload.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                .map(value -> mapResponseToJson(jsonParser, value))
        ).toList()
                .subscribe(
                        jsonElementList -> {
                            JsonObject clientResult = new JsonObject();
                            for (RequestWrapper<JsonElement> jsonElement : jsonElementList) {
                                clientResult.add(jsonElement.header, jsonElement.payload);
                            }
//                    response.setResponse(clientResult);
                        },
                        error -> System.out.println("error"),
                        () -> System.out.println("completed"));
    }



    public static void main(String[] args) {
        ServiceAggrigator serviceMonitor = new ServiceAggrigator();
        serviceMonitor.getServicesStatus();
    }

}
