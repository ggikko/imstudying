package me.ggikko.rxjava;


public abstract class Subscriber<T> implements Subscription {
    protected final void request(long n) {

    }

    public void unsubscribe() {

    }

    public boolean isUnsubscribed() {
        return false;
    }
}
