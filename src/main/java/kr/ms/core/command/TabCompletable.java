package kr.ms.core.command;

import java.util.List;

@FunctionalInterface
public interface TabCompletable {
    List<String> apply();
}
