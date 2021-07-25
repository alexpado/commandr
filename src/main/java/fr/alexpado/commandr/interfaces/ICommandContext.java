package fr.alexpado.commandr.interfaces;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

/**
 * Represents a command context.
 *
 * A command context is an instance holding specific information about the command being executed and to pass to a
 * command.
 *
 * @param <T> The type of the {@link ICommand} emitter.
 */
public interface ICommandContext<T> {

    /**
     * Associate the class {@code C} to the provided instance of {@link C}.
     *
     * This have a direct consequence while resolving a {@link Method} parameter when using {@link Method#invoke(Object,
     * Object...)}.
     *
     * @param clazz    The class to associate
     * @param instance The instance of this class
     * @param <C>      The type of the class.
     */
    <C> void registerClass(@NotNull Class<C> clazz, C instance);

    /**
     * Retrieve the instance associated to the provided class.
     *
     * @param clazz The class from which to retrieve the instance.
     * @param <C>   The type of the class.
     *
     * @return The maybe-null instance of the provided class.
     */
    @Nullable <C> C getRegisteredClass(@NotNull Class<C> clazz);

    /**
     * Check if the provided class has been registered within this {@link ICommandContext}.
     *
     * @param clazz The class to check.
     * @param <C>   The type of the class.
     *
     * @return True if the instance has been registered, false otherwise.
     */
    <C> boolean hasClass(Class<C> clazz);

    /**
     * Retrieve the emitter object {@link T} being responsible of this {@link ICommandContext} creation.
     *
     * @return The source object.
     */
    @NotNull
    T getEmitter();

}
