package fr.alexpado.commandr.interfaces;

import org.jetbrains.annotations.Nullable;

public interface ICommandResult {

    /**
     * Check whether a result is available (command executed).
     *
     * @return True if a result is available, false otherwise.
     */
    boolean isResultAvailable();

    /**
     * Retrieve the result of the command execution. The result may be null.
     *
     * @return The command's result.
     *
     * @throws IllegalStateException
     *         Threw if {@link #isResultAvailable()} is false.
     */
    @Nullable Object getResult();

}
