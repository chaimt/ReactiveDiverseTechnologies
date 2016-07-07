package com.tikal.fleet.rxjava;

import rx.Observable;

/**
 * Created by chaimturkel on 6/28/16.
 */
public class SimpleRange {

    public static void main(String[] args) {
        Observable.range(1, 5).subscribe(
                number -> System.out.println(number),
                error -> System.out.println("error"),
                () -> System.out.println("completed")
        );

    }
}
