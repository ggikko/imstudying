package me.ggikko.rxjava.tools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public final class Scheduler {

    static final Scheduler IO = RxJavaPlugins.initIoScheduler(new Callable<Scheduler>() {
        @Override
        public Scheduler call() throws Exception {
            return IoHolder.DEFAULT;
        }
    });

    public static Scheduler io() {
        return RxJavaPlugins.onIoScheduler(IO);
    }
}
