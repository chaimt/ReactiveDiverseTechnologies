package com.tikal.fleet.rxjava;

import rx.Observable;
import rx.exceptions.Exceptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by chaimturkel on 7/5/16.
 */
public class ErrorExample {

    static public void onError(){
        Observable.just("Hello!")
                .map(input -> { throw new RuntimeException(); })
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error!")
                );
    }

    static String transform(String input) throws IOException {
        throw new IOException();
    }

    static public void propagateError(){
        Observable.just("Hello!")
                .map(input -> {
                    try {
                        return transform(input);
                    } catch (Throwable t) {
                        throw Exceptions.propagate(t);
                    }
                })

                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error! - " + error.toString()),
                        () -> System.out.println("finished")
                );
    }

    static public void transferError() {

        Observable.just("Hello!")
                .map(input -> { throw new RuntimeException(); })
                .onErrorReturn(error -> "Empty result")
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error!")
                );
    }

    static public void retryOnError(){
        Observable.interval(1, TimeUnit.SECONDS)
                .map(input -> {
                    if (Math.random() < .5) {
                        throw new RuntimeException();
                    }
                    return "Success " + input;
                })
                .subscribe(System.out::println);
    }

//    onErrorResumeNext( ) — instructs an Observable to emit a sequence of items if it encounters an error
//    onErrorReturn( ) — instructs an Observable to emit a particular item when it encounters an error
//    onExceptionResumeNext( ) — instructs an Observable to continue emitting items after it encounters an exception (but not another variety of throwable)
//    retry( ) — if a source Observable emits an error, resubscribe to it in the hopes that it will complete without error
//    retryWhen( ) — if a source Observable emits an error, pass that error to another Observable to determine whether to resubscribe to the source


    public static void main(String[] args) {
//        propagateError();
//        transferError();
        retryOnError();
    }


}
