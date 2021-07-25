package fr.alexpado.commandr.data.commands;

import fr.alexpado.commandr.annotations.Command;
import fr.alexpado.commandr.annotations.Param;
import fr.alexpado.commandr.impl.CommandImpl;
import fr.alexpado.commandr.interfaces.ICommand;
import fr.alexpado.commandr.interfaces.ICommandContext;
import fr.alexpado.commandr.interfaces.ICommandMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class HelloCommand extends CommandImpl<Object> {

    /**
     * Retrieve the command meta for this {@link ICommand}.
     *
     * @return An {@link ICommandMeta} instance.
     */
    @Override
    public @NotNull ICommandMeta<Object> getMeta() {

        return new ICommandMeta<>() {

            @Override
            public @NotNull String getLabel() {

                return "hello";
            }

            @Override
            public String getDescription() {

                return "Hello World for Unit Tests";
            }

            @Override
            public @Nullable Map<String, List<String>> getOptions(@NotNull ICommandContext<Object> context) {

                return null;
            }
        };
    }

    @Command(value = "world", order = 0)
    public String sayHelloWorld() {

        return "Hello World!";
    }

    @Command("world [name]")
    public String sayHelloWorldWithName(@Param("name") String name) {

        return String.format("Hello World, %s!", name);
    }

    @Command(value = "[name]", order = 1)
    public String sayHelloName(@Param("name") String name) {

        return String.format("Hello, %s!", name);
    }

    @Command("echo text...")
    public String echoText(@Param("text") String text) {
        return text;
    }

    @Command("cmd desc")
    public String getHelloDescription(HelloCommand command) {
        return command.getMeta().getDescription();
    }
}
