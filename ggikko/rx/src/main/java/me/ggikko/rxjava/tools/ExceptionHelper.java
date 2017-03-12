package me.ggikko.rxjava.tools;

/**
 * Created by ggikko on 2017. 3. 12..
 */
public class ExceptionHelper {
    public static RuntimeException wrapOrThrow(Throwable error) {
        if (error instanceof Error) {
            throw (Error)error;
        }
        if (error instanceof RuntimeException) {
            return (RuntimeException)error;
        }
        return new RuntimeException(error);
    }

}
