package me.ggikko.streams;

//RxJava와 Reactive Streams가 조금 다름..음...
public interface Subscription {
    public void request(long n);

    public void cancel();
}
