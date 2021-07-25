package fr.alexpado.commandr.impl;

import fr.alexpado.commandr.interfaces.ICommand;
import fr.alexpado.commandr.interfaces.ICommandContext;
import fr.alexpado.commandr.interfaces.ICommandEvent;
import fr.alexpado.commandr.interfaces.ICommandHandler;
import org.jetbrains.annotations.NotNull;

public class CommandEvent<T> implements ICommandEvent<T> {

    private final ICommandContext<T> context;
    private final ICommandHandler<T> handler;
    private final ICommand<T>        command;
    private final T                  emitter;
    private final String             message;
    private       boolean            cancelled;

    public CommandEvent(ICommandContext<T> context, ICommandHandler<T> handler, ICommand<T> command, T emitter, String message) {
        this.context   = context;
        this.handler   = handler;
        this.command   = command;
        this.emitter   = emitter;
        this.message   = message;
        this.cancelled = false;
    }

    /**
     * Retrieve the {@link ICommandContext} created associated with {@link ICommandEvent}.
     *
     * @return An {@link ICommandContext} implementation instance.
     */
    @Override
    public @NotNull ICommandContext<T> getContext() {
        return this.context;
    }

    /**
     * Retrieve the {@link ICommandHandler} that created this {@link ICommandEvent}.
     *
     * @return An {@link ICommandHandler} implementation instance.
     */
    @Override
    public @NotNull ICommandHandler<T> getHandler() {
        return this.handler;
    }

    /**
     * Retrieve the {@link ICommand} targeted by this {@link ICommandEvent}.
     *
     * @return An {@link ICommandHandler} implementation instance.
     */
    @Override
    public @NotNull ICommand<T> getCommand() {
        return this.command;
    }

    /**
     * Retrieve the emitter object {@link T} being responsible of this {@link ICommandEvent} creation.
     *
     * @return The source object.
     */
    @Override
    public @NotNull T getEmitter() {
        return this.emitter;
    }

    /**
     * Check if the {@link ICommandHandler} will cancel the {@link ICommand} execution.
     *
     * @return True if the execution will be cancelled, false otherwise..
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Define if this {@link ICommandHandler} should cancel the {@link ICommand} execution.
     *
     * @param cancelled True if the execution should be cancelled, false otherwise.
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get the message that triggered this {@link ICommandEvent}.
     *
     * @return The message representing the command.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
