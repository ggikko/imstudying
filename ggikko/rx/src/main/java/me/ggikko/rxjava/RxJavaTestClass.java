package me.ggikko.rxjava;

import lombok.extern.slf4j.Slf4j;
import me.ggikko.rxjava.tools.Observable;

/**
 * Created by ggikko on 2017. 3. 5..
 */

@Slf4j
public class RxJavaTestClass {

    public static void main(String[] args) {
        rxJava();
    }

    private static void rxJava() {
        Observable<String> just = Observable.just("Football");

        just.subscribeOn(Schedulers.io());
    }
}
