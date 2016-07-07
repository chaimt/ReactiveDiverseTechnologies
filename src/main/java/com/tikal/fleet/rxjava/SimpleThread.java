package com.tikal.fleet.rxjava;

import rx.Observable;

/**
 * Created by chaimturkel on 6/28/16.
 */
public class SimpleThread {

    public static void main(String[] args) {
        Observable.fromCallable(() -> {return 33;}).map(item -> item*2).subscribe(
                number -> System.out.println(number),
                error -> System.out.println("error"),
                () -> System.out.println("completed")
        );
    }
}
