package uk.co.animandosolutions.mcdev.mysterystat.command;

import static net.minecraft.text.Text.literal;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.server.command.ServerCommandSource;

public interface CommandDefinition {
	public record Argument<T>(String name, ArgumentType<T> argumentType) {
		public Argument(String name, ArgumentType<T> argumentType) {
			this.name = name;
			this.argumentType = argumentType;
		}
	}

	String getCommand();

	int execute(CommandContext<ServerCommandSource> context);

	default Argument<?>[] getArguments() {
		return new Argument<?>[] {};
	}

	default void sendMessage(ServerCommandSource source, String message) {
		source.sendFeedback(() -> literal(message), false);
	}

	default String getArgument(CommandContext<ServerCommandSource> context, String argumentName) {
		return this.getArgument(context, argumentName, String.class);
	}

	default <V> V getArgument(CommandContext<ServerCommandSource> context, String argumentName, final Class<V> clazz) {
		return context.getArgument(argumentName, clazz);
	}
}
