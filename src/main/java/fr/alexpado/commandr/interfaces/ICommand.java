package fr.alexpado.commandr.interfaces;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface representing a command that can be executed.
 *
 * @param <T> The type of the {@link ICommand} emitter.
 */
public interface ICommand<T> {

    /**
     * Retrieve the command meta for this {@link ICommand}.
     *
     * @return An {@link ICommandMeta} instance.
     */
    @NotNull
    ICommandMeta<T> getMeta();

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
    @Nullable Object execute(@NotNull ICommandContext<T> context, @NotNull String message) throws Exception;
}
