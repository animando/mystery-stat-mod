package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static net.minecraft.text.Text.literal;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getObjectiveWithName;

import java.util.Optional;
import java.util.function.Supplier;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper;

record Score(String player, int score) {
	public Score(String player, int score) {
		this.player = player;
		this.score = score;
	}

	public String toString() {
		return format("%s: %s", player, score);
	}
}

public class ListScores implements CommandDefinition {

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = context.getSource().getServer().getScoreboard();
		String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME);
		String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);

		Optional<ScoreboardObjective> maybeObjective = extracted(source, scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			return 0;
		}

		ScoreboardObjective objective = maybeObjective.get();
		listScores(source, scoreboard, objectiveName, objective);

		return 1;
	}

	private void listScores(ServerCommandSource source, ServerScoreboard scoreboard, String objectiveName,
			ScoreboardObjective objective) {
		var list = scoreboard.getScoreboardEntries(objective).stream().map(it -> {
			var owner = it.owner();
			var score = it.value();
			return new Score(owner, score);

		}).sorted(this::scoreDescending).toList();
		var topThree = list.subList(0, Math.min(3, list.size()));

		sendMessage(source, format("Mystery Stat Leaderboard (%s)", objectiveName));
		if (list.size() == 0) {
			sendMessage(source, "<empty list>");
		}
		for (int position = topThree.size(); position >= 1; position--) {
			sendMessage(source, formatListEntry(topThree.get(position - 1), position));
		}
	}

	private Optional<ScoreboardObjective> extracted(ServerCommandSource source, ServerScoreboard scoreboard,
			String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = getObjectiveWithName(scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			sendMessage(source, format("Unknown objective: %s", objectiveName));
		}
		return maybeObjective;
	}

	private String formatListEntry(final Score entry, final int place) {
		return format("%d: %s (%s)", place, entry.player(), entry.score());
	}

	@Override
	public String getCommand() {
		return "list";
	}

	private int scoreDescending(Score score1, Score score2) {
		return score2.score() - score1.score();
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] { new CommandDefinition.Argument<>(
				CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string()) };
	}

}
