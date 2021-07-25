package fr.alexpado.commandr;

import fr.alexpado.commandr.exceptions.SyntaxErrorException;
import fr.alexpado.commandr.impl.CommandContext;
import fr.alexpado.commandr.impl.CommandEvent;
import fr.alexpado.commandr.impl.CommandResult;
import fr.alexpado.commandr.interfaces.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class CommandHandler<T> implements ICommandHandler<T> {

    private final Map<String, ICommand<T>>          commands;
    private final Map<Integer, ICommandListener<T>> listeners;

    public CommandHandler() {

        this.commands  = new HashMap<>();
        this.listeners = new HashMap<>();
    }

    /**
     * Retrieve an {@link Optional} {@link ICommand} matching the provided label.
     *
     * @param label
     *         The label associated to the {@link ICommand} to retrieve
     *
     * @return An {@link Optional} {@link ICommand}
     */
    @Override
    public @NotNull Optional<ICommand<T>> getCommand(@NotNull String label) {

        return Optional.ofNullable(this.commands.get(label));
    }

    /**
     * Register the provided command.
     *
     * @param command
     *         The {@link ICommand} to register.
     */
    @Override
    public void register(@NotNull ICommand<T> command) {

        String label = command.getMeta().getLabel();
        if (this.getCommand(label).isPresent()) {
            throw new IllegalStateException("Tried to register an already registered command.");
        }
        this.commands.put(label, command);
    }

    /**
     * Get a {@link Map} mapping every registered command label and their corresponding {@link ICommand}.
     *
     * @return A {@link Map} of {@link String} and {@link ICommand}
     */
    @Override
    public @NotNull Map<String, ICommand<T>> getCommands() {

        return this.commands;
    }

    /**
     * Create an {@link ICommandContext} instance from the provided command emitter {@link T}.
     *
     * @param emitter
     *         The command emitter.
     *
     * @return An instance of {@link ICommandContext} implementation.
     */
    @Override
    public @NotNull ICommandContext<T> createContext(@NotNull T emitter) {

        return new CommandContext<>(emitter);
    }

    /**
     * Start the command execution from the provided emitter. Please note that even if this method is called, this do
     * not ensure that a command will be executed.
     *
     * @param emitter
     *         The object {@link T} emitting a command.
     * @param message
     *         The user input corresponding to a command.
     *
     * @return An {@link ICommandResult} implementation representing the result of the command.
     */
    @Override
    public ICommandResult handle(@NotNull T emitter, @NotNull String message) {

        String prefix = this.getApplicablePrefix(emitter).toLowerCase();
        String msg    = message.toLowerCase();
        String label  = msg.split(" ")[0].replace(prefix, "");

        Optional<ICommand<T>> optionalCommand = this.getCommand(label);

        if (optionalCommand.isEmpty()) {
            this.listeners.values().forEach(listener -> listener.onCommandNotFound(this, emitter, message));
            return new CommandResult();
        }

        ICommand<T>        command = optionalCommand.get();
        ICommandContext<T> context = this.createContext(emitter);
        this.listeners.values().forEach(listener -> listener.onContextCreated(context));

        ICommandEvent<T> event = new CommandEvent<>(context, this, command, emitter, message);
        this.listeners.values().forEach(listener -> listener.onCommandExecution(event));

        if (event.isCancelled()) {
            return new CommandResult();
        }

        try {
            return new CommandResult(command.execute(context, message));
        } catch (SyntaxErrorException e) {
            this.listeners.values().forEach(listener -> listener.onSyntaxError(context));
            return new CommandResult();
        } catch (InvocationTargetException e) {
            // If an exception is thrown within a command, it will be wrapped into an InvocationTargetException
            // by the JDK. The real exception thrown within the command will be the cause.
            Throwable cause = e.getCause();

            if (cause instanceof Exception) {
                this.listeners.values().forEach(listener -> listener.onCommandException(event, (Exception) cause));
            } else {
                this.listeners.values().forEach(listener -> listener.onCommandException(event, e));
            }
            return new CommandResult();
        } catch (Exception e) {
            this.listeners.values().forEach(listener -> listener.onCommandException(event, e));
            return new CommandResult();
        }
    }

    /**
     * Register the provided {@link ICommandListener} within this {@link ICommandHandler}.
     *
     * @param listener
     *         The {@link ICommandListener} to register.
     */
    @Override
    public void addEventListener(@NotNull ICommandListener<T> listener) {

        int hashCode = listener.hashCode();
        if (this.listeners.containsKey(hashCode)) {
            throw new IllegalStateException("Tried to register an already registered listener.");
        }
        this.listeners.put(hashCode, listener);
    }

    /**
     * Remove the provided {@link ICommandListener} from this {@link ICommandHandler}.
     *
     * @param listener
     *         The {@link ICommandListener} to unregister.
     */
    @Override
    public void removeEventListener(@NotNull ICommandListener<T> listener) {

        int hashCode = listener.hashCode();
        if (!this.listeners.containsKey(hashCode)) {
            throw new IllegalStateException("Tried to unregister an unregistered listener.");
        }
        this.listeners.remove(hashCode);
    }
}
