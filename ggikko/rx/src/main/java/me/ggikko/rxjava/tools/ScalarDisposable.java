package me.ggikko.rxjava.tools;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public final class ScalarDisposable<T>
        extends AtomicInteger
        implements QueueDisposable<T>, Runnable {

    private static final long serialVersionUID = 3880992722410194083L;

    final Observer<? super T> observer;

    final T value;

    static final int START = 0;
    static final int FUSED = 1;
    static final int ON_NEXT = 2;
    static final int ON_COMPLETE = 3;

    public ScalarDisposable(Observer<? super T> observer, T value) {
        this.observer = observer;
        this.value = value;
    }

    @Override
    public boolean offer(T value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public boolean offer(T v1, T v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public T poll() throws Exception {
        if (get() == FUSED) {
            lazySet(ON_COMPLETE);
            return value;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return get() != FUSED;
    }

    @Override
    public void clear() {
        lazySet(ON_COMPLETE);
    }

    @Override
    public void dispose() {
        set(ON_COMPLETE);
    }

    @Override
    public boolean isDisposed() {
        return get() == ON_COMPLETE;
    }

    @Override
    public int requestFusion(int mode) {
        if ((mode & SYNC) != 0) {
            lazySet(FUSED);
            return SYNC;
        }
        return NONE;
    }

    @Override
    public void run() {
        if (get() == START && compareAndSet(START, ON_NEXT)) {
            observer.onNext(value);
            if (get() == ON_NEXT) {
                lazySet(ON_COMPLETE);
                observer.onComplete();
            }
        }
    }
}

