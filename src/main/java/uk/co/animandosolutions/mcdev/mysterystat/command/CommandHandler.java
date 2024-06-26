package uk.co.animandosolutions.mcdev.mysterystat.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.util.Arrays;
import java.util.List;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class CommandHandler {

	public static final CommandHandler INSTANCE = new CommandHandler();

	List<CommandDefinition> subCommands = Arrays.asList(new AddMysteryStat(), new ListScores(), new ClearObjective(),
			new ActivateObjective(), new PublishScores(), new PopulateObjective());

	private CommandHandler() {
	}

	public void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var commandBuilder = literal(CommandConstants.Commands.MYSTERYSTAT)
					.requires(Permissions.require(ListScores.PERMISSION_LIST_CURRENT, 4));

			subCommands.forEach(subCommandDefinition -> {

				String subCommand = subCommandDefinition.getCommand();
				var subCommandBuilder = literal(subCommand)
						.requires(Permissions.require(subCommandDefinition.getPermission(), 4));

				var args = subCommandDefinition.getArguments();
				RequiredArgumentBuilder<ServerCommandSource, ?> argBuilder = null;
				for (int i = args.length - 1; i >= 0; i--) {
					var arg = args[i];
					var finalArg = i == args.length - 1;

					RequiredArgumentBuilder<ServerCommandSource, ?> localArgBuilder = argument(arg.name(),
							arg.argumentType());
					if (arg.suggestionProvider().isPresent()) {
						localArgBuilder.suggests(arg.suggestionProvider().get());
					}
					localArgBuilder.requires(Permissions.require(arg.permission(), 4));
					if (finalArg || args[i + 1].optional()) {
						localArgBuilder = localArgBuilder.executes(subCommandDefinition::execute);
					}
					if (argBuilder != null) {
						argBuilder = localArgBuilder.then(argBuilder);
					} else {
						argBuilder = localArgBuilder;
					}
				}
				if (argBuilder != null) {
					subCommandBuilder.then(argBuilder);
				}
				if (args.length == 0 || args[0].optional()) {
					subCommandBuilder.executes(subCommandDefinition::execute);
				}

				commandBuilder.then(subCommandBuilder);
			});

			dispatcher.register(commandBuilder);
		});
	}
}
