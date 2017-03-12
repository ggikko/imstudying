package me.ggikko.rxjava.tool;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public class ObservableJust<T> extends Observable<T> {
    private final T value;

    public ObservableJust(final T value) {
        this.value = value;
    }

    @Override
    protected void subscribeActual(Observer<? super T> s) {
        ScalarDisposable<T> sd = new ScalarDisposable<T>(s, value);
        s.onSubscribe(sd);
        sd.run();
    }
}
