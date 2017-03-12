package me.ggikko.rxjava.tools;

/**
 * Created by ggikko on 2017. 3. 11..
 */
public interface Function<T, R> {
    R apply(T t) throws Exception;
}
