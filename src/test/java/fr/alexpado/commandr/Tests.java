package fr.alexpado.commandr;


import fr.alexpado.commandr.data.AutoCommandHandler;
import fr.alexpado.commandr.data.commands.HelloCommand;
import fr.alexpado.commandr.interfaces.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class Tests {

    @Test
    @DisplayName("Should execute the command returning 'Hello World!'")
    public void shouldReturnHelloWorldTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        ICommandResult result = handler.handle(this, "!hello world");

        Assertions.assertTrue(result.isResultAvailable(), "No result available.");
        Assertions.assertEquals("Hello World!", result.getResult());
    }

    @Test
    @DisplayName("Should execute the command returning 'Hello World, <name>!'")
    public void shouldReturnHelloWorldWithNameTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        ICommandResult result = handler.handle(this, "!hello world John");

        Assertions.assertTrue(result.isResultAvailable(), "No result available.");
        Assertions.assertEquals("Hello World, John!", result.getResult());
    }

    @Test
    @DisplayName("Should execute the command returning 'Hello, <name>!'")
    public void shouldReturnHelloWithNameTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        ICommandResult result = handler.handle(this, "!hello John");

        Assertions.assertTrue(result.isResultAvailable(), "No result available.");
        Assertions.assertEquals("Hello, John!", result.getResult());
    }

    @Test
    @DisplayName("Should execute the command returning the filler syntax")
    public void shouldReturnBackTextTests() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        ICommandResult result = handler.handle(this, "!hello echo It's working !");

        Assertions.assertTrue(result.isResultAvailable(), "No result available.");
        Assertions.assertEquals("It's working !", result.getResult());
    }

    @Test
    @DisplayName("Should cancel any command execution")
    public void shouldCancelCommandExecutionTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());

        handler.addEventListener(new ICommandListener<>() {

            @Override
            public void onCommandExecution(@NotNull ICommandEvent<Object> event) {

                event.setCancelled(true);
            }

            @Override
            public void onCommandException(@NotNull ICommandEvent<Object> event, @NotNull Exception throwable) {}

            @Override
            public void onSyntaxError(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onContextCreated(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onCommandNotFound(@NotNull ICommandHandler<Object> handler, @NotNull Object emitter, @NotNull String command) {}
        });

        ICommandResult result = handler.handle(this, "!hello world");
        Assertions.assertFalse(result.isResultAvailable(), "The command was not cancelled.");
    }

    @Test
    @DisplayName("Should return the command description provided as registered class.")
    public void shouldReturnCommandDescriptionWithClassRegisterTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        HelloCommand            command = new HelloCommand();
        handler.register(command);

        handler.addEventListener(new ICommandListener<>() {

            @Override
            public void onCommandExecution(@NotNull ICommandEvent<Object> event) {}

            @Override
            public void onCommandException(@NotNull ICommandEvent<Object> event, @NotNull Exception throwable) {}

            @Override
            public void onSyntaxError(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onContextCreated(@NotNull ICommandContext<Object> context) {

                context.registerClass(HelloCommand.class, command);
            }

            @Override
            public void onCommandNotFound(@NotNull ICommandHandler<Object> handler, @NotNull Object emitter, @NotNull String command) {}
        });

        ICommandResult result = handler.handle(this, "!hello cmd desc");
        Assertions.assertTrue(result.isResultAvailable(), "No result available.");
        Assertions.assertEquals(command.getMeta().getDescription(), result.getResult());
    }

    @Test
    @DisplayName("Should call the onCommandException listener")
    public void shouldCallbackCommandExceptionTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        CountDownLatch latch = new CountDownLatch(1);

        handler.addEventListener(new ICommandListener<>() {

            @Override
            public void onCommandExecution(@NotNull ICommandEvent<Object> event) {}

            @Override
            public void onCommandException(@NotNull ICommandEvent<Object> event, @NotNull Exception throwable) {

                latch.countDown();
            }

            @Override
            public void onSyntaxError(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onContextCreated(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onCommandNotFound(@NotNull ICommandHandler<Object> handler, @NotNull Object emitter, @NotNull String command) {}
        });

        ICommandResult result = handler.handle(this, "!hello cmd desc");

        Assertions.assertFalse(result.isResultAvailable(), "A result has been found.");
        Assertions.assertTimeout(Duration.ofSeconds(3), (Executable) latch::await, "The error was probably not thrown within the 3 seconds time limit.");
        Assertions.assertEquals(0, latch.getCount());
    }

    @Test
    @DisplayName("Should call the onSyntaxError listener")
    public void shouldCallbackSyntaxErrorTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        CountDownLatch latch = new CountDownLatch(1);

        handler.addEventListener(new ICommandListener<>() {

            @Override
            public void onCommandExecution(@NotNull ICommandEvent<Object> event) {}

            @Override
            public void onCommandException(@NotNull ICommandEvent<Object> event, @NotNull Exception throwable) {}

            @Override
            public void onSyntaxError(@NotNull ICommandContext<Object> context) {

                latch.countDown();
            }

            @Override
            public void onContextCreated(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onCommandNotFound(@NotNull ICommandHandler<Object> handler, @NotNull Object emitter, @NotNull String command) {}
        });

        ICommandResult result = handler.handle(this, "!hello this command does not exists");

        Assertions.assertFalse(result.isResultAvailable(), "A result has been found.");
        Assertions.assertTimeout(Duration.ofSeconds(3), (Executable) latch::await, "The error was probably not thrown within the 3 seconds time limit.");
        Assertions.assertEquals(0, latch.getCount());
    }

    @Test
    @DisplayName("Should call the onSyntaxError listener")
    public void shouldCallbackCommandNotFoundTest() {

        ICommandHandler<Object> handler = new AutoCommandHandler();
        handler.register(new HelloCommand());
        CountDownLatch latch = new CountDownLatch(1);

        handler.addEventListener(new ICommandListener<>() {

            @Override
            public void onCommandExecution(@NotNull ICommandEvent<Object> event) {}

            @Override
            public void onCommandException(@NotNull ICommandEvent<Object> event, @NotNull Exception throwable) {}

            @Override
            public void onSyntaxError(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onContextCreated(@NotNull ICommandContext<Object> context) {}

            @Override
            public void onCommandNotFound(@NotNull ICommandHandler<Object> handler, @NotNull Object emitter, @NotNull String command) {

                latch.countDown();
            }
        });

        ICommandResult result = handler.handle(this, "!this command does not exists");

        Assertions.assertFalse(result.isResultAvailable(), "A result has been found.");
        Assertions.assertTimeout(Duration.ofSeconds(3), (Executable) latch::await, "The error was probably not thrown within the 3 seconds time limit.");
        Assertions.assertEquals(0, latch.getCount());
    }


}
