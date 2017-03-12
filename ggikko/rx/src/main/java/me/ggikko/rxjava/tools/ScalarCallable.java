package me.ggikko.rxjava.tools;

import java.util.concurrent.Callable;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public interface ScalarCallable<T> extends Callable<T> {

    // overridden to remove the throws Exception
    @Override
    T call();
}

