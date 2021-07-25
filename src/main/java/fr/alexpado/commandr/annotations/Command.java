package fr.alexpado.commandr.annotations;

import fr.alexpado.syntaxic.interfaces.ISyntax;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation allowing to identify a method used to execute commands. The method annotated with {@link Command} needs to
 * be public.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * Syntax string that will be used to trigger the annotated method.
     *
     * @return A syntax string
     *
     * @see ISyntax
     */
    String value() default "";

    /**
     * Define the priority compared to other {@link Command}s. The order is decided in the natural order, meaning that 0
     * will have higher priority over 1. This is very useful when two methods can be matched with the same input. Eg:
     * <ul>
     *     <li><code>hello world</code></li>
     *     <li><code>hello [name]</code></li>
     * </ul>
     * <p>
     * These can be both triggered with the input <code>hello world</code>, as <code>[name]</code> will match <code>world</code>.
     * In this situation, it generally better to give the non-eager syntax a lower priority than the eager one.
     * <ul>
     *      <li><code>hello world</code> with order <b>0</b></li>
     *      <li><code>hello [name]</code> with order <b>1</b></li>
     * </ul>
     *
     * @return The order priority.
     */
    int order() default Integer.MIN_VALUE;

}
