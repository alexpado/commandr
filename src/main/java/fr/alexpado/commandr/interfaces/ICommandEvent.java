package fr.alexpado.commandr.interfaces;

import org.jetbrains.annotations.NotNull;

/**
 * Interface representing the event when an {@link ICommand} is executed.
 *
 * @param <T> The type of the {@link ICommand} emitter.
 */
public interface ICommandEvent<T> {

    /**
     * Retrieve the {@link ICommandContext} created associated with {@link ICommandEvent}.
     *
     * @return An {@link ICommandContext} implementation instance.
     */
    @NotNull
    ICommandContext<T> getContext();

    /**
     * Retrieve the {@link ICommandHandler} that created this {@link ICommandEvent}.
     *
     * @return An {@link ICommandHandler} implementation instance.
     */
    @NotNull
    ICommandHandler<T> getHandler();

    /**
     * Retrieve the {@link ICommand} targeted by this {@link ICommandEvent}.
     *
     * @return An {@link ICommandHandler} implementation instance.
     */
    @NotNull
    ICommand<T> getCommand();

    /**
     * Retrieve the emitter object {@link T} being responsible of this {@link ICommandEvent} creation.
     *
     * @return The source object.
     */
    @NotNull
    T getEmitter();

    /**
     * Check if the {@link ICommandHandler} will cancel the {@link ICommand} execution.
     *
     * @return True if the execution will be cancelled, false otherwise..
     */
    boolean isCancelled();

    /**
     * Define if this {@link ICommandHandler} should cancel the {@link ICommand} execution.
     *
     * @param cancelled True if the execution should be cancelled, false otherwise.
     */
    void setCancelled(boolean cancelled);

    /**
     * Get the message that triggered this {@link ICommandEvent}.
     *
     * @return The message representing the command.
     */
    String getMessage();
}
