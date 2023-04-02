package kr.ms.core.repo;

import kr.ms.core.command.MSArgument;

public interface MSArgumentRepository {

    void registerArguments(MSArgument<?>... argument);
    MSArgument<?> getArgument(Class<?> clazz);

}
