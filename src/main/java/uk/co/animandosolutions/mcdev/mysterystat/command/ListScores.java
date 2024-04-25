package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static java.util.Optional.of;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getObjectiveWithName;

import java.util.List;
import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.MysteryObjectiveSuggestionProvider;

public class ListScores implements CommandDefinition {

	static final String PERMISSION_LIST_ANY = "mysterystat.list.any";
	static final String PERMISSION_LIST_CURRENT = "mysterystat.list.current";

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = context.getSource().getServer().getScoreboard();
		Optional<String> objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME)
				.map(ObjectiveHelper::fullyQualifiedObjectiveName);

		Optional<ScoreboardObjective> maybeObjective = objectiveNameArg.isPresent()
				? getScoreboardObjective(source, scoreboard, objectiveNameArg.get())
				: Optional.of(scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.LIST));

		if (maybeObjective.isEmpty()) {
			return 0;
		}

		ScoreboardObjective objective = maybeObjective.get();
		listScores(source, scoreboard, objective);

		return 1;
	}

	private void listScores(ServerCommandSource source, ServerScoreboard scoreboard, ScoreboardObjective objective) {
		List<ObjectiveHelper.Score> topThree = ObjectiveHelper.getTopThree(scoreboard, objective);

		sendMessage(source, format("Mystery Stat Leaderboard (%s)", objective.getDisplayName().getLiteralString()));
		if (topThree.size() == 0) {
			sendMessage(source, "<empty list>");
		}
		for (int position = topThree.size(); position >= 1; position--) {
			sendMessage(source, formatListEntry(topThree.get(position - 1), position));
		}
	}

	private Optional<ScoreboardObjective> getScoreboardObjective(ServerCommandSource source,
			ServerScoreboard scoreboard, String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = getObjectiveWithName(scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			sendMessage(source, format("Unknown objective: %s", objectiveName));
		}
		return maybeObjective;
	}

	private String formatListEntry(final ObjectiveHelper.Score entry, final int place) {
		return format("%d: %s (%s)", place, entry.player(), entry.score());
	}

	@Override
	public String getCommand() {
		return "list";
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] {
				new CommandDefinition.Argument<>(CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string(),
						true, PERMISSION_LIST_ANY, of(MysteryObjectiveSuggestionProvider.INSTANCE)) };
	}

	@Override
	public String getPermission() {
		return PERMISSION_LIST_CURRENT;
	}

}
