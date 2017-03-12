package me.ggikko.rxjava.tools;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public interface SimpleQueue<T> {

    boolean offer(T value);

    boolean offer(T v1, T v2);

    T poll() throws Exception;

    boolean isEmpty();

    void clear();
}
