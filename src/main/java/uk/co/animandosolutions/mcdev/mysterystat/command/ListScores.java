package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static net.minecraft.text.Text.literal;
import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

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
	private static final Logger logger = LOGGER;

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = context.getSource().getServer().getScoreboard();
		String objectiveName = format("mysterystat_%s", context.getArgument("objectiveName", String.class));

		Optional<ScoreboardObjective> maybeObjective = scoreboard.getObjectives().stream()
				.filter(it -> it.getName().equals(objectiveName)).findFirst();
		if (maybeObjective.isEmpty()) {
			source.sendFeedback(() -> unknown(objectiveName), false);
			return 0;
		}
		ScoreboardObjective objective = maybeObjective.get();
		var list = scoreboard.getScoreboardEntries(objective).stream().map(it -> {
			var owner = it.owner();
			var score = it.value();
			return new Score(owner, score);

		}).sorted(this::scoreDescending).toList();
		logger.info("full list size: " + list.size());
		var topThree = list.subList(0, Math.min(3, list.size()));
		logger.info("full list size: " + list.size());

		source.sendFeedback(() -> literal(format("Mystery Stat Leaderboard (%s)", objectiveName)), false);
		for (int position = topThree.size(); position >= 1; position--) {
			source.sendFeedback(formatListEntry(topThree.get(position - 1), position), false);
		}

		return 1;
	}

	private Supplier<Text> formatListEntry(final Score entry, final int place) {
		return () -> literal(format("%d: %s (%s)", place, entry.player(), entry.score()));
	}

	private MutableText unknown(String objectiveName) {
		return literal(format("Unknown objective: %s", objectiveName));
	}

	@Override
	public String getCommand() {
		return "list";
	}

	private int scoreDescending(Score score1, Score score2) {
		return score2.score() - score1.score();
	}

}
