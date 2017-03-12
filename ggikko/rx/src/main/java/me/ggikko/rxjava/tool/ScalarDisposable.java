package me.ggikko.rxjava.tool;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public class ScalarDisposable<T> implements Disposable, Runnable {

    final Observer<? super T> observer;

    final T value;

    public ScalarDisposable(Observer<? super T> observer, T value) {
        this.observer = observer;
        this.value = value;
    }

    public T get() {
        return value;
    }

    @Override
    public void run() {
        observer.onNext(value);
//        if (get() == ON_NEXT) {
//            lazySet(ON_COMPLETE);
        observer.onComplete();
//        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isDisposed() {
        return false;
    }
}
