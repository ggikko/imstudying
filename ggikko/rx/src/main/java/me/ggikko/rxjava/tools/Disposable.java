package me.ggikko.rxjava.tools;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public interface Disposable {
    void dispose();

    boolean isDisposed();
}