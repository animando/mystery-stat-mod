package uk.co.animandosolutions.mcdev.mysterystat.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class CommandHandler {

	public static final CommandHandler INSTANCE = new CommandHandler();

	List<CommandDefinition> subCommands = Arrays.asList(new AddMysteryStat(), new ListScores(), new ClearObjectives());

	private CommandHandler() {
	}

	public void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var commandBuilder = literal("mysterystat").requires(hasOperatorPermission());

			subCommands.forEach(it -> {
				var subCommandBuilder = literal(it.getCommand());
				Arrays.asList(it.getArguments()).forEach(arg -> {
					subCommandBuilder.then(argument(arg.name(), arg.argumentType()));
				});
				subCommandBuilder.executes(it::execute);
				commandBuilder.then(subCommandBuilder);
			});

			dispatcher.register(commandBuilder);
		});
	}

	private Predicate<ServerCommandSource> hasOperatorPermission() {
		return source -> source.hasPermissionLevel(4);
	}

}
