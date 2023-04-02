package kr.ms.core.repo.impl;

import kr.ms.core.command.MSArgument;
import kr.ms.core.repo.MSArgumentRepository;
import kr.ms.core.util.AsyncExecutor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class $MSArgumentRepositoryImpl implements MSArgumentRepository {

    private final HashMap<String, List<MSArgument<?>>> argumentMap;

    public $MSArgumentRepositoryImpl() {
        argumentMap = new HashMap<>();
        registerArguments(
                new MSArgument<>(Player.class, "플레이어", (buf) -> Bukkit.getServer().getPlayer(buf), () -> null, false, 0),
                new MSArgument<>(Integer.class, "정수(int)", (buf) -> {
                    try { return Integer.parseInt(buf); } catch (NumberFormatException ignored) { return null; }
                }, () -> null, false, 0),
                new MSArgument<>(Long.class, "정수(long)", (buf) -> {
                    try { return Long.parseLong(buf); } catch (NumberFormatException ignored) { return null; }
                }, () -> null, false, 0),
                new MSArgument<>(Double.class, "실수", (buf) -> {
                    try { return Double.parseDouble(buf); } catch (NumberFormatException ignored) { return null; }
                }, () -> null, false, 0),
                new MSArgument<>(String.class, "문자열", (buf) -> buf, () -> null, false, 0)
        );
    }

    @Override public void registerArguments(MSArgument<?>... arguments) {
        for(MSArgument<?> argument : arguments)
            argumentMap.computeIfAbsent(argument.getArgumentClassName(), (unused)->new ArrayList<>()).add(argument);
        sortAsync();
    }

    @Override
    public MSArgument<?> getArgument(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        if(simpleName.equalsIgnoreCase("Int")) simpleName = "Integer";
        if(argumentMap.containsKey(simpleName)) {
            List<MSArgument<?>> list = argumentMap.get(simpleName);
            if(list.isEmpty()) return null;
            else return list.get(0);
        }
        return null;
    }

    private void sortAsync() {
        AsyncExecutor.run(()-> argumentMap.forEach((unused, value)-> value.sort(MSArgument::compareTo)));
    }
}
