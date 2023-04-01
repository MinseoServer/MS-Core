package kr.ms.core.command;

@FunctionalInterface
public interface Castable<T> {
    T cast(String string);
}
