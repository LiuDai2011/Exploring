package Exploring.type.func;

@FunctionalInterface
public interface Function<T, R> {
    R run(T arg);
}
