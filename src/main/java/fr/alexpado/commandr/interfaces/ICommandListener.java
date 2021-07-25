package fr.alexpado.commandr.interfaces;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for listing to various event of and {@link ICommandHandler}
 *
 * @param <T> The type of the {@link ICommand} emitter.
 */
public interface ICommandListener<T> {

    /**
     * Called when an {@link ICommand} will be executed. You can cancel the {@link ICommand} execution anytime during
     * this method execution by calling {@link ICommandEvent#setCancelled(boolean)}.
     *
     * @param event An {@link ICommandEvent} implementation instance.
     */
    void onCommandExecution(@NotNull ICommandEvent<T> event);

    /**
     * Called when an {@link ICommand} execution throws an exception. You may want to handle error display here.
     *
     * @param event     An {@link ICommandEvent} implementation instance.
     * @param throwable The {@link Exception} that has been thrown.
     */
    void onCommandException(@NotNull ICommandEvent<T> event, @NotNull Exception throwable);

    /**
     * Called when an {@link ICommand} has been found based on the user input, but no annotated method could be found to
     * handle the user input.
     *
     * @param context An {@link ICommandContext} implementation instance.
     */
    void onSyntaxError(@NotNull ICommandContext<T> context);

    /**
     * Called when an {@link ICommandContext} is created. You may want to use this method to register your custom object
     * for parameters injection using {@link ICommandContext#registerClass(Class, Object)}.
     *
     * @param context An {@link ICommandEvent} implementation instance.
     */
    void onContextCreated(@NotNull ICommandContext<T> context);

    /**
     * Called when an {@link ICommand} could not be found based on the user input. You may want to handle error display
     * here.
     *
     * @param handler The {@link ICommandHandler} that was not able to find the {@link ICommand}.
     * @param emitter The emitter of the command.
     * @param command The command that was executed.
     */
    void onCommandNotFound(@NotNull ICommandHandler<T> handler, @NotNull T emitter, @NotNull String command);
}
