package me.ggikko.rxjava.tool;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public class RxJavaPlugins {

    public static <T> Observable<T> onAssembly(Observable<T> source) {
        return source;
    }

    public static <T> Observer<? super T> onSubscribe(Observable<T> source, Observer<? super T> observer) {
        return observer;
    }
}
