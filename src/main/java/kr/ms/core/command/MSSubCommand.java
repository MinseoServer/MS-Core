package kr.ms.core.command;

import kr.ms.core.Core;
import kr.ms.core.annotation.ArgumentLabel;
import kr.ms.core.annotation.NullableArgument;
import kr.ms.core.annotation.Subcommand;
import kr.ms.core.context.MessageContext;
import kr.ms.core.util.Pair;
import kr.ms.core.command.wrapper.CommandSenderWrapper;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MSSubCommand {

    private final ArrayList<Pair<MSArgument<?>, Boolean>> arguments = new ArrayList<>();
    private final Subcommand annotation;
    private final Method function;
    private final MSCommand parent;
    private final String helpLine;
    public MSSubCommand(Subcommand annotation, Method method, MSCommand parent) {
        this.annotation = annotation;
        this.function = method;
        this.parent = parent;
        StringBuilder builder = new StringBuilder();
        builder.append("§6/").append(parent.getCommand()).append(" ").append(annotation.subCommand());
        Parameter[] params = method.getParameters();
        for(int i = 0; i < params.length; i++) {
            if(i == 0) continue;
            Parameter param = params[i];
            Class<?> paramClass = param.getType();
            MSArgument<?> argument = Core.getArgumentRepository().getArgument(paramClass);
            if(argument != null) {
                if (param.isAnnotationPresent(NullableArgument.class)) arguments.add(new Pair<>(argument, false));
                else arguments.add(new Pair<>(argument, true));
                String label = argument.getLabel();
                if(param.isAnnotationPresent(ArgumentLabel.class))
                    label = param.getAnnotation(ArgumentLabel.class).value();
                builder.append(" ").append(label);
            }
        }
        helpLine = builder.append(" : §f").append(annotation.description()).toString();
    }

    public Subcommand getAnnotation() { return annotation; }

    public void execute(CommandSenderWrapper sender, String[] args) {
        if(annotation.isOp() && !sender.isOp()) {
            sender.sendMessage(MessageContext.COMMAND_PERMISSION_ERROR);
        } else if(!sender.hasPermission(annotation.permission())) {
            sender.sendMessage(MessageContext.COMMAND_PERMISSION_ERROR);
        } else {
            List<Object> argumentList = new ArrayList<>();
            argumentList.add(sender);
            for(int i = 0; i < arguments.size(); i++) {
                Pair<MSArgument<?>, Boolean> it = arguments.get(i);
                Object obj;
                try { obj = it.getFirst().cast(args[i]); } catch (Exception e) { obj = null; }
                if(it.getSecond() && obj == null) {
                    String label = it.getFirst().getLabel();
                    if(function.getParameters()[i + 1].isAnnotationPresent(ArgumentLabel.class))
                        label = function.getParameters()[i + 1].getAnnotation(ArgumentLabel.class).value();
                    sender.sendMessage(String.format(MessageContext.COMMAND_NOT_NULL_ARGUMENT, label));
                    return;
                } else argumentList.add(obj);
            }
            try {
                function.invoke(parent, argumentList.toArray());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(MessageContext.COMMAND_INVALID_ARGUMENT_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void printHelpMessage(CommandSenderWrapper sender) { sender.sendMessage(helpLine); }

    public MSArgument<?> getArgument(CommandSenderWrapper sender, int index) {
        try {
            if(!sender.isOp() && annotation.isOp()) return null;
            else if(!annotation.permission().isEmpty() && sender.hasPermission(annotation.permission())) return arguments.get(index).getFirst();
            else if(annotation.permission().isEmpty()) return arguments.get(index).getFirst();
            else return null;
        } catch (Exception e) { return null; }
    }

}
