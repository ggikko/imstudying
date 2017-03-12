package me.ggikko.rxjava.tool;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public interface Observer<T> {
    void onSubscribe(Disposable d);

    void onNext(T value);

    void onError(Throwable e);

    void onComplete();
}
