package me.ggikko.rxjava.tool;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public interface Disposable {
    void dispose();
    boolean isDisposed();
}
