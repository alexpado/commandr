package fr.alexpado.commandr.data;

import fr.alexpado.commandr.CommandHandler;
import org.jetbrains.annotations.NotNull;

public class AutoCommandHandler extends CommandHandler<Object> {

    public AutoCommandHandler() {

        super();
    }

    /**
     * Retrieve the prefix that should be used to match any command for the provided emitter.
     *
     * @param emitter
     *         The object emitting a command.
     *
     * @return The prefix to use with this emitter.
     */
    @Override
    public @NotNull String getApplicablePrefix(@NotNull Object emitter) {

        return "!";
    }
}
