package malangcute.bellytime.bellytimeCustomer.global.exception;

import java.util.function.Function;

public class ExceptionConfig {

    public interface ExceptionFunction<T, R> {
        R apply(T r) throws Exception;
    }

    public static <T, R> Function<T, R> wrap(ExceptionFunction<T, R> f) {
        return (T r) -> {
            try {
                return f.apply(r);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
