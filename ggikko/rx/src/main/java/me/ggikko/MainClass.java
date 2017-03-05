package me.ggikko;

import me.ggikko.streams.Publisher;
import me.ggikko.streams.Subscriber;
import me.ggikko.streams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ggikko on 2017. 3. 5..
 */
public class MainClass {
    public static void main(String[] args) {
        rxjava();
        reactiveStreams();
    }

    private static void reactiveStreams() {
        final List<Integer> listMock = Arrays.asList(1, 2, 3, 4, 5, 6);

        Publisher publisher = new Publisher() {
            Iterator<Integer> itr = listMock.iterator();

            public void subscribe(final Subscriber subscriber) {
                subscriber.onSubscribe(new Subscription() {
                    public void request(long n) {
                        while (n-- > 0) {
                            if (itr.hasNext()) {
                                subscriber.onNext(itr.next());
                            } else {
                                subscriber.onComplete();
                            }
                        }
                    }

                    public void cancel() {

                    }
                });
            }
        };

        publisher.subscribe(new Subscriber<Integer>() {
            public void onSubscribe(Subscription subscription) {
                subscription.request(listMock.size());
            }

            public void onNext(Integer integer) {
                System.out.printf("event : " + integer + "\n");
            }

            public void onError(Throwable t) {

            }

            public void onComplete() {
                System.out.printf("onComplete");
            }
        });
    }

    private static void rxjava() {

    }
}
