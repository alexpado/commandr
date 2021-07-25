package fr.alexpado.commandr.impl;

import fr.alexpado.commandr.interfaces.ICommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandContext<T> implements ICommandContext<T> {

    private final Map<Class<?>, Object> classRegister;
    private final T                     emitter;

    public CommandContext(T emitter) {

        this.emitter       = emitter;
        this.classRegister = new HashMap<>();
    }

    /**
     * Associate the class {@code C} to the provided instance of {@link C}.
     *
     * This have a direct consequence while resolving a {@link Method} parameter when using {@link Method#invoke(Object,
     * Object...)}.
     *
     * @param clazz    The class to associate
     * @param instance The instance of this class
     */
    @Override
    public <C> void registerClass(@NotNull Class<C> clazz, C instance) {

        this.classRegister.put(clazz, instance);
    }

    /**
     * Retrieve the instance associated to the provided class.
     *
     * @param clazz The class from which to retrieve the instance.
     *
     * @return The maybe-null instance of the provided class.
     */
    @Override
    public <C> @Nullable C getRegisteredClass(@NotNull Class<C> clazz) {
        //noinspection unchecked
        return (C) this.classRegister.get(clazz);
    }

    /**
     * Check if the provided class has been registered within this {@link ICommandContext}.
     *
     * @param clazz The class to check.
     *
     * @return True if the instance has been registered, false otherwise.
     */
    @Override
    public <C> boolean hasClass(Class<C> clazz) {
        return this.classRegister.containsKey(clazz);
    }

    /**
     * Retrieve the emitter object {@link T} being responsible of this {@link ICommandContext} creation.
     *
     * @return The source object.
     */
    @Override
    public @NotNull T getEmitter() {
        return this.emitter;
    }
}
