package me.ggikko.streams;

/**
 * Created by ggikko on 2017. 3. 5..
 */
public interface Publisher<T> {
    public void subscribe(Subscriber<? super T> s);


}
