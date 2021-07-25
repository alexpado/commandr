package fr.alexpado.commandr.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

/**
 * Interface representing an {@link ICommand} handler.
 *
 * @param <T>
 *         The type of the {@link ICommand} emitter.
 *
 * @author alexpado
 */
public interface ICommandHandler<T> {

    /**
     * Retrieve an {@link Optional} {@link ICommand} matching the provided label.
     *
     * @param label
     *         The label associated to the {@link ICommand} to retrieve
     *
     * @return An {@link Optional} {@link ICommand}
     */
    @NotNull Optional<ICommand<T>> getCommand(@NotNull String label);

    /**
     * Register the provided command.
     *
     * @param command
     *         The {@link ICommand} to register.
     */
    void register(@NotNull ICommand<T> command);

    /**
     * Get a {@link Map} mapping every registered command label and their corresponding {@link ICommand}.
     *
     * @return A {@link Map} of {@link String} and {@link ICommand}
     */
    @NotNull Map<String, ICommand<T>> getCommands();

    /**
     * Start the command execution from the provided emitter. Please note that even if this method is called, this do
     * not ensure that a command will be executed.
     *
     * @param emitter
     *         The object {@link T} emitting a command.
     * @param message
     *         The user input corresponding to a command.
     *
     * @return An {@link ICommandResult} implementation.
     */
    ICommandResult handle(@NotNull T emitter, @NotNull String message);

    /**
     * Create an {@link ICommandContext} instance from the provided command emitter {@link T}.
     *
     * @param emitter
     *         The command emitter.
     *
     * @return An instance of {@link ICommandContext} implementation.
     */
    @NotNull ICommandContext<T> createContext(@NotNull T emitter);

    /**
     * Retrieve the prefix that should be used to match any command for the provided emitter.
     *
     * @param emitter
     *         The object {@link T} emitting a command.
     *
     * @return The prefix to use with this emitter.
     */
    @NotNull String getApplicablePrefix(@NotNull T emitter);

    /**
     * Register the provided {@link ICommandListener} within this {@link ICommandHandler}.
     *
     * @param listener
     *         The {@link ICommandListener} to register.
     */
    void addEventListener(@NotNull ICommandListener<T> listener);

    /**
     * Remove the provided {@link ICommandListener} from this {@link ICommandHandler}.
     *
     * @param listener
     *         The {@link ICommandListener} to unregister.
     */
    void removeEventListener(@NotNull ICommandListener<T> listener);
}
