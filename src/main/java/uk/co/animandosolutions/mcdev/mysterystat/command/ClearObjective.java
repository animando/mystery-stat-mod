package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static java.util.Optional.of;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getObjectiveWithName;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.MysteryObjectiveSuggestionProvider;

public class ClearObjective implements CommandDefinition {

	static final String PERMISSION = "mysterystat.clear";

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = source.getServer().getScoreboard();

		String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME).orElseThrow();
		String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);

		Optional<ScoreboardObjective> maybeObjective = getScoreboardObjective(source, scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			return 0;
		}
		ScoreboardObjective objective = maybeObjective.get();

		sendMessage(source, format("Removing mystery objective %s", objectiveName));

		removeObjective(scoreboard, objective);

		return 1;
	}

	private Optional<ScoreboardObjective> getScoreboardObjective(ServerCommandSource source,
			ServerScoreboard scoreboard, String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = getObjectiveWithName(scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			sendMessage(source, format("Unknown objective: %s", objectiveName));
		}
		return maybeObjective;
	}

	private void removeObjective(ServerScoreboard scoreboard, ScoreboardObjective objective) {
		scoreboard.removeObjective(objective);
	}

	@Override
	public String getCommand() {
		return "clear";
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] {
				new CommandDefinition.Argument<>(CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string(),
						false, PERMISSION, of(MysteryObjectiveSuggestionProvider.INSTANCE)), };
	}

	@Override
	public String getPermission() {
		return PERMISSION;
	}

}
