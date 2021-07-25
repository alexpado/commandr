package fr.alexpado.commandr.impl;

import fr.alexpado.commandr.interfaces.ICommandResult;
import org.jetbrains.annotations.Nullable;

public class CommandResult implements ICommandResult {

    private final boolean hasResult;

    @Nullable
    private final Object  data;

    /**
     * Create a new instance of {@link CommandResult} without a result.
     */
    public CommandResult() {

        this.hasResult = false;
        this.data      = null;
    }

    /**
     * Create a new instance of {@link CommandResult} providing an object returned from a command.
     *
     * @param data
     *         The data returned by a command.
     */
    public CommandResult(@Nullable Object data) {

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
}
