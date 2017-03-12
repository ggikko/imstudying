package me.ggikko.rxjava.tools;

import org.omg.CORBA.ObjectHelper;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public abstract class Observable<T> implements ObservableSource<T> {

    protected abstract void subscribeActual(Observer<? super T> observer);

    public static <T> Observable<T> just(T item) {
        return RxJavaPlugins.onAssembly(new ObservableJust<T>(item));
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableSubscribeOn<T>(this, scheduler));
    }

    @Override
    public final void subscribe(Observer<? super T> observer) {
//        ObjectHelper.requireNonNull(observer, "observer is null");
        try {
            observer = RxJavaPlugins.onSubscribe(this, observer);

//            ObjectHelper.requireNonNull(observer, "Plugin returned null Observer");

            subscribeActual(observer);
        } catch (NullPointerException e) { // NOPMD
            throw e;
        } catch (Throwable e) {
//            Exceptions.throwIfFatal(e);
            // can't call onError because no way to know if a Disposable has been set or not
            // can't call onSubscribe because the call might have set a Subscription already
            RxJavaPlugins.onError(e);

            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            npe.initCause(e);
            throw npe;
        }
    }
}
