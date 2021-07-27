package fr.alexpado.commandr.impl;

import fr.alexpado.commandr.interfaces.ICommandContext;
import fr.alexpado.commandr.interfaces.ICommandResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandResult<T> implements ICommandResult<T> {

    private final boolean hasResult;

    @Nullable
    private final Object data;

    @NotNull
    private final ICommandContext<T> context;

    /**
     * Create a new instance of {@link CommandResult} without a result.
     *
     * @param context
     *         The {@link ICommandContext} currently being used.
     */
    public CommandResult(@NotNull ICommandContext<T> context) {

        this.context = context;

        this.hasResult = false;
        this.data      = null;
    }

    /**
     * Create a new instance of {@link CommandResult} providing an object returned from a command.
     *
     * @param data
     *         The data returned by a command.
     * @param context
     *         The {@link ICommandContext} currently being used.
     */
    public CommandResult(@NotNull ICommandContext<T> context, @Nullable Object data) {

        this.context = context;

        this.hasResult = true;
        this.data      = data;
    }

    /**
     * Check whether a result is available (command executed).
     *
     * @return True if a result is available, false otherwise.
     */
    @Override
    public boolean isResultAvailable() {

        return this.hasResult;
    }

    /**
     * Retrieve the result of the command execution. The result may be null.
     *
     * @return The command's result.
     *
     * @throws IllegalStateException
     *         Threw if {@link #isResultAvailable()} is false.
     */
    @Override
    public @Nullable Object getResult() {

        if (!this.hasResult) {
            throw new IllegalStateException("There is no result available.");
        }

        return this.data;
    }

    /**
     * Retrieve the context that was in-use when this {@link ICommandResult} was created.
     *
     * @return An {@link ICommandContext}
     */
    @Override
    public @NotNull ICommandContext<T> getContext() {

        return this.context;
    }
}
