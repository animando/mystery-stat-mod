package uk.co.animandosolutions.mcdev.mysterystat.command;

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

}
