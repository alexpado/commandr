package fr.alexpado.commandr.interfaces;

import fr.alexpado.syntaxic.interfaces.ISyntax;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface ICommandMeta<T> {

    /**
     * Retrieves the label associated with an {@link ICommand}.
     *
     * @return A label.
     */
    @NotNull
    String getLabel();

    /**
     * Retrieves the description associated with an {@link ICommand}. This will be used in the help menu.
     *
     * @return A description.
     */
    @Nullable
    String getDescription();

    /**
     * Retrieve the {@link Map} associating an {@link ISyntax}'s name to its possible values.
     *
     * @param context The current {@link ICommandContext} for this execution.
     *
     * @return A {@link Map}
     */
    @Nullable
    Map<String, List<String>> getOptions(@NotNull ICommandContext<T> context);

}
