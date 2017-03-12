package me.ggikko.streams;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ggikko on 2017. 3. 5..
 */

@Slf4j
public class RxStreamTestClass {
    public static void main(String[] args) {
        reactiveStreams();
    }

    private static void reactiveStreams() {
        final List<Integer> listMock = Arrays.asList(1, 2, 3, 4, 5, 6);

        Publisher pub = subscriber -> {
            subscriber.onSubscribe(new Subscription() {
                Iterator<Integer> itr = listMock.iterator();

                public void request(long n) {
                    try {
                        while (n-- > 0) {
                            if (itr.hasNext()) {
                                subscriber.onNext(itr.next());
                            }
                        }
                    } catch (RuntimeException e) {
                        subscriber.onError(e);
                    } finally {
                        subscriber.onComplete();
                    }
                }

                public void cancel() {

                }
            });
        };

        Publisher pubSub = sub -> {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.execute(() -> pub.subscribe(sub));
        };

        Publisher mapPub = mapOperator(pubSub, t -> t * 3);

        mapPub.subscribe(new Subscriber<Integer>()

        {
            public void onSubscribe(Subscription subscription) {
                subscription.request(listMock.size());
            }

            public void onNext(Integer integer) {
                log.debug("event : " + integer);
            }

            public void onError(Throwable t) {
                log.debug("onError");
            }

            public void onComplete() {
                log.debug("onComplete");
            }
        });
    }

    private static Publisher<Integer> mapOperator(Publisher<Integer> pub, Function<Integer, Integer> function) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub.onSubscribe(s);
                    }

                    @Override
                    public void onNext(Integer t) {
                        try {
                            sub.onNext(function.apply(t));
                        } catch (Exception e) {
                            sub.onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        sub.onError(t);
                    }

                    @Override
                    public void onComplete() {
                        sub.onComplete();
                    }
                });
            }
        };
    }

    ;
}
