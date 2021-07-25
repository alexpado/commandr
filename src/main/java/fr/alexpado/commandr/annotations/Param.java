package fr.alexpado.commandr.annotations;

import fr.alexpado.syntaxic.interfaces.ISyntax;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a method's parameter as a parameter receiving a dynamic value coming from the user's
 * input.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {

    /**
     * The name of the parameter used in the syntax string.
     *
     * @return Variable name
     *
     * @see ISyntax#getName()
     */
    String value();
}
