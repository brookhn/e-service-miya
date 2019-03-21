package com.pp.server.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class RxJavaTest {

    public static void testRxJava()
    {
        AtomicBoolean onComplete = new AtomicBoolean(false);

        Observable<Map<String, Object>>  observable = Observable.create(new ObservableOnSubscribe<Map<String, Object>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, Object>> observableEmitter) throws Exception {

            }
        });

        Observer<Map<String, Object>> observer = new Observer<Map<String, Object>>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("define");
            }

            @Override
            public void onNext(Map<String, Object> stringObjectMap) {
                System.out.println("");
            }

            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onComplete() {
                onComplete.compareAndSet(false, true);
            }
        };
    }

    public static void main(String args[])
    {

    }
}
