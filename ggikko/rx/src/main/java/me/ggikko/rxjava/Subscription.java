package me.ggikko.rxjava;

/**
 * Created by ggikko on 2017. 3. 5..
 */
public interface Subscription {
    void unsubscribe();

    boolean isUnsubscribed();
}
