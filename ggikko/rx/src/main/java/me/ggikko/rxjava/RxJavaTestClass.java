package me.ggikko.rxjava;

import lombok.extern.slf4j.Slf4j;
import me.ggikko.rxjava.tool.Disposable;
import me.ggikko.rxjava.tool.Observable;
import me.ggikko.rxjava.tool.Observer;

/**
 * Created by ggikko on 2017. 3. 5..
 */

@Slf4j
public class RxJavaTestClass {

    public static void main(String[] args) {
        rxJava();
    }

    private static void rxJava() {
        Observable<Integer> observable = Observable.just(1);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                log.debug("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                log.debug("onNext : " + value);
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError");
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        });
    }
}
