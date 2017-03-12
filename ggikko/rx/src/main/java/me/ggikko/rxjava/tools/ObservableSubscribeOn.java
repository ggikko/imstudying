package me.ggikko.rxjava.tools;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public class ObservableSubscribeOn {
    final Scheduler scheduler;

    public ObservableSubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }
}
