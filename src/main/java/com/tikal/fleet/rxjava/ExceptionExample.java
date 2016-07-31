package com.tikal.fleet.rxjava;

import rx.Observable;

/**
 * Created by chaimturkel on 7/21/16.
 */
public class ExceptionExample {

    public static void main(String[] args) {
        Observable.just(1, 2, 0,3)
        .map(v -> 100/v)
                .subscribe(value -> System.out.println(value),
                        error -> System.out.println(error.getMessage()),
                        () -> System.out.println("finished"));
    }
}
