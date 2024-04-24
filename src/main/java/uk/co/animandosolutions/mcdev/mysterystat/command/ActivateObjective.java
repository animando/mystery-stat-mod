package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getObjectiveWithName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.setObjectiveDisplay;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;

public class ActivateObjective implements CommandDefinition {

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = context.getSource().getServer().getScoreboard();
		String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME).orElseThrow();
		String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);

		Optional<ScoreboardObjective> maybeObjective = getScoreboardObjective(source, scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			return 0;
		}

		ScoreboardObjective objective = maybeObjective.get();

		activateObjective(source, scoreboard, objectiveName, objective);
		
		return 1;
	}

	private void activateObjective(ServerCommandSource source, ServerScoreboard scoreboard, String objectiveName,
			ScoreboardObjective objective) {
		setObjectiveDisplay(scoreboard, objective);
	}

	private Optional<ScoreboardObjective> getScoreboardObjective(ServerCommandSource source, ServerScoreboard scoreboard,
			String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = getObjectiveWithName(scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			sendMessage(source, format("Unknown objective: %s", objectiveName));
		}
		return maybeObjective;
	}

	@Override
	public String getCommand() {
		return "activate";
	}


	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] { new CommandDefinition.Argument<>(
				CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string(), false) };
	}

}
