package com.tikal.fleet.rxjava;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by chaimturkel on 7/5/16.
 */
public class BasicExample {

    static public void iterateExample(){
        Observable.just(1, 2 ,3 ,4)
                .filter( val -> val % 2 == 0 )
                .map( val -> val*2 )
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Complete!");
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext: " + value);
            }
        });
    }

    static public void createExample(){
        Observable.OnSubscribe<String> subscribeFunction = (s) -> {
            Subscriber subscriber = (Subscriber)s;

            for (int ii = 0; ii < 10; ii++) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("Pushed value " + ii);
                }
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        };

        Observable createdObservable = Observable.create(subscribeFunction);

        createdObservable.subscribe(
                (incomingValue) -> System.out.println("incomingValue " + incomingValue),
                (error) -> System.out.println("Something went wrong" + ((Throwable)error).getMessage()),
                () -> System.out.println("This observable is finished")
        );
    }



    public static void main(String[] args) {
        BasicExample.iterateExample();
    }
}
