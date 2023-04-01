package kr.ms.core.repo;

import kr.ms.core.command.STArgument;

public interface STArgumentRepository {

    void registerArguments(STArgument<?>... argument);
    STArgument<?> getArgument(Class<?> clazz);

}
