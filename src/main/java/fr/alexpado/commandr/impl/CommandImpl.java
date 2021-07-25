package fr.alexpado.commandr.impl;

import fr.alexpado.commandr.annotations.Command;
import fr.alexpado.commandr.annotations.Param;
import fr.alexpado.commandr.exceptions.SyntaxErrorException;
import fr.alexpado.commandr.interfaces.ICommand;
import fr.alexpado.commandr.interfaces.ICommandContext;
import fr.alexpado.commandr.interfaces.ICommandHandler;
import fr.alexpado.commandr.interfaces.ICommandListener;
import fr.alexpado.syntaxic.SyntaxService;
import fr.alexpado.syntaxic.SyntaxUtils;
import fr.alexpado.syntaxic.interfaces.IMatchingResult;
import fr.alexpado.syntaxic.interfaces.ISyntaxContainer;
import fr.alexpado.syntaxic.interfaces.ISyntaxService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandImpl<T> implements ICommand<T> {

    /**
     * Called by the {@link ICommandHandler} in use when this {@link ICommand} is triggered.
     * <p>
     * The results of this call could be anything, but the {@link ICommandHandler} may not support it.
     *
     * @param context The current {@link ICommandContext} for this execution.
     * @param message The user input corresponding to a command.
     *
     * @return An object being the result of the execution, or <code>null</code>.
     *
     * @throws Exception Thrown when something goes wrong. This is the recommended method instead of using a big
     *                   try-catch. If the {@link Exception} goes into the {@link ICommandHandler#handle(Object,
     *                   String)} method, the {@link Exception} will be reported to all {@link ICommandListener}. You
     *                   can still handle yourself some user related exceptions that should produce a user-friendly
     *                   output.
     * @see ICommandHandler#handle(Object, String)
     */
    @Override
    public @Nullable Object execute(@NotNull ICommandContext<T> context, @NotNull String message) throws Exception {

        Map<Method, ISyntaxContainer> syntaxMap = new HashMap<>();
        Map<String, List<String>>     options   = Optional.ofNullable(this.getMeta().getOptions(context)).orElseGet(HashMap::new);
        for (Method declaredMethod : this.getClass().getDeclaredMethods()) {
            Command command = declaredMethod.getAnnotation(Command.class);

            if (command != null) {
                syntaxMap.put(declaredMethod, SyntaxUtils.toContainer(options, command.value(), command.order()));
            }
        }

        List<String> userInput = Arrays.stream(message.trim().split(" "))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        ISyntaxService<Method>            service        = new SyntaxService<>(syntaxMap);
        String                            matchData      = String.join(" ", userInput.subList(1, userInput.size()));
        Optional<IMatchingResult<Method>> matchingResult = service.getMatchingResult(matchData);

        if (matchingResult.isEmpty()) {
            throw new SyntaxErrorException();
        }

        IMatchingResult<Method> result     = matchingResult.get();
        Method                  exec       = result.getIdentifier();
        List<Object>            parameters = new ArrayList<>();

        for (Parameter parameter : result.getIdentifier().getParameters()) {
            if (parameter.isAnnotationPresent(Param.class)) {
                Param param = parameter.getAnnotation(Param.class);

                Optional<String> optionalValue = result.getParameter(param.value());

                if (parameter.getType() == Optional.class) {
                    parameters.add(optionalValue);
                } else {
                    parameters.add(optionalValue.orElse(null));
                }
            } else if (context.hasClass(parameter.getType())) {
                parameters.add(context.getRegisteredClass(parameter.getType()));
            } else if (parameter.getType().isInstance(context)) {
                parameters.add(context);
            } else {
                throw new IllegalStateException("Unable to complete type " + parameter.getType().getName());
            }
        }

        return exec.invoke(this, parameters.toArray());
    }
}
