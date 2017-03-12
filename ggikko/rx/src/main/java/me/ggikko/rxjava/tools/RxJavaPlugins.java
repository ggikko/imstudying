package me.ggikko.rxjava.tools;

import org.omg.CORBA.ObjectHelper;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public class RxJavaPlugins {
    static volatile Function<Observable, Observable> onObservableAssembly;
    static volatile BiFunction<Observable, Observer, Observer> onObservableSubscribe;
    static volatile Consumer<Throwable> errorHandler;
    static volatile Function<Scheduler, Scheduler> onIoHandler;
    static volatile Function<Callable<Scheduler>, Scheduler> onInitIoHandler;

    public static <T> Observable<T> onAssembly(Observable<T> source) {
        Function<Observable, Observable> f = onObservableAssembly;
        if (f != null) {
            return apply(f, source);
        }
        return source;
    }

    public static <T> Observer<? super T> onSubscribe(Observable<T> source, Observer<? super T> observer) {
        BiFunction<Observable, Observer, Observer> f = onObservableSubscribe;
        if (f != null) {
            return apply(f, source, observer);
        }
        return observer;
    }

    public static void onError(Throwable error) {
        Consumer<Throwable> f = errorHandler;

        if (error == null) {
            error = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }

        if (f != null) {
            try {
                f.accept(error);
                return;
            } catch (Throwable e) {
                // Exceptions.throwIfFatal(e); TODO decide
                e.printStackTrace(); // NOPMD
                uncaught(e);
            }
        }

        error.printStackTrace(); // NOPMD
        uncaught(error);
    }

    static void uncaught(Throwable error) {
        Thread currentThread = Thread.currentThread();
        Thread.UncaughtExceptionHandler handler = currentThread.getUncaughtExceptionHandler();
        handler.uncaughtException(currentThread, error);
    }

    public static Scheduler initIoScheduler(Callable<Scheduler> defaultScheduler) {
//        ObjectHelper.requireNonNull(defaultScheduler, "Scheduler Callable can't be null");
        Function<Callable<Scheduler>, Scheduler> f = onInitIoHandler;
        if (f == null) {
            return callRequireNonNull(defaultScheduler);
        }
        return applyRequireNonNull(f, defaultScheduler);
    }

    static Scheduler applyRequireNonNull(Function<Callable<Scheduler>, Scheduler> f, Callable<Scheduler> s) {
        return ObjectHelper.requireNonNull(apply(f, s), "Scheduler Callable result can't be null");
    }

    static <T, R> R apply(Function<T, R> f, T t) {
        try {
            return f.apply(t);
        } catch (Throwable ex) {
            throw ExceptionHelper.wrapOrThrow(ex);
        }
    }

    static <T, U, R> R apply(BiFunction<T, U, R> f, T t, U u) {
        try {
            return f.apply(t, u);
        } catch (Throwable ex) {
            throw ExceptionHelper.wrapOrThrow(ex);
        }
    }

    public static Scheduler onIoScheduler(Scheduler defaultScheduler) {
        Function<Scheduler, Scheduler> f = onIoHandler;
        if (f == null) {
            return defaultScheduler;
        }
        return apply(f, defaultScheduler);
    }

}
