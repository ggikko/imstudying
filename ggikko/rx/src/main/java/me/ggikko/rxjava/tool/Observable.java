package me.ggikko.rxjava.tool;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public abstract class Observable<T> {

    public static <T> Observable<T> just(T item) {
        return RxJavaPlugins.onAssembly(new ObservableJust<T>(item));
    }

    public final void subscribe(Observer<? super T> observer) {
        try {
            observer = RxJavaPlugins.onSubscribe(this, observer);
            subscribeActual(observer);
        } catch (NullPointerException e) { // NOPMD
            throw e;
        } catch (Throwable e) {
//            Exceptions.throwIfFatal(e);
            // can't call onError because no way to know if a Disposable has been set or not
            // can't call onSubscribe because the call might have set a Subscription already
//            RxJavaPlugins.onError(e);

            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            npe.initCause(e);
            throw npe;
        }
    }

    protected abstract void subscribeActual(Observer<? super T> observer);
}
