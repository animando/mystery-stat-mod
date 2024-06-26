package uk.co.animandosolutions.mcdev.mysterystat.objectives;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardCriterion.RenderType;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.text.MutableText;

public class ObjectiveHelper {
	public static int scoreDescending(Score score1, Score score2) {
		return score2.score() - score1.score();
	}

	public static List<Score> getTopThree(Scoreboard scoreboard, ScoreboardObjective objective) {
		List<Score> list = getAllObjectiveScores(scoreboard, objective).stream()
				.sorted(ObjectiveHelper::scoreDescending).toList();
		List<Score> topThree = list.subList(0, Math.min(3, list.size()));
		return topThree;
	}

	public static List<Score> getAllObjectiveScores(Scoreboard scoreboard, ScoreboardObjective objective) {
		return scoreboard.getScoreboardEntries(objective).stream().map(it -> {
			var owner = it.owner();
			var score = it.value();
			return new Score(owner, score);
		}).toList();
	}

	public static record Score(String player, int score) {
		public Score(String player, int score) {
			this.player = player;
			this.score = score;
		}

		public String toString() {
			return format("%s: %s", player, score);
		}
	}

	public static Optional<ScoreboardObjective> getObjectiveWithName(ServerScoreboard scoreboard,
			String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = scoreboard.getObjectives().stream()
				.filter(it -> it.getName().equals(objectiveName)).findFirst();
		return maybeObjective;
	}

	public static List<ScoreboardObjective> getAllMysteryStatObjectives(ServerScoreboard scoreboard) {
		List<ScoreboardObjective> objectives = scoreboard.getObjectives().stream()
				.filter(it -> it.getName().startsWith(ObjectiveConstants.OBJECTIVE_PREFIX)).toList();
		return objectives;
	}

	public static List<ScoreboardObjective> getAllNonMysteryStatObjectives(ServerScoreboard scoreboard) {
		List<ScoreboardObjective> objectives = scoreboard.getObjectives().stream()
				.filter(it -> !it.getName().startsWith(ObjectiveConstants.OBJECTIVE_PREFIX)).toList();
		return objectives;
	}
	
	public static List<String> getAllNonMysteryStatObjectiveIdentifiers(ServerScoreboard scoreboard) {
		return getAllNonMysteryStatObjectives(scoreboard).stream().map(it -> it.getName()).toList();
	}
	
	public static List<String> getAllMysteryStatObjectiveIdentifiers(ServerScoreboard scoreboard) {
		return getAllMysteryStatObjectives(scoreboard).stream().map(it -> it.getName()).map(ObjectiveHelper::stripPrefix).toList();
	}

	public static String fullyQualifiedObjectiveName(String objectiveNameArg) {
		return format("%s_%s", ObjectiveConstants.OBJECTIVE_PREFIX, objectiveNameArg);
	}

	public static String stripPrefix(String objectiveNameArg) {
		return objectiveNameArg.replace(format("%s_", ObjectiveConstants.OBJECTIVE_PREFIX), "");
	}

	public static ScoreboardObjective createObjective(ServerScoreboard scoreboard, String objectiveName,
			MutableText displayName, ScoreboardCriterion criterion) {
		return scoreboard.addObjective(objectiveName, criterion, displayName, RenderType.INTEGER, true, null);
	}

	public static void setObjectiveDisplay(ServerScoreboard scoreboard, ScoreboardObjective objective) {
		scoreboard.setObjectiveSlot(ScoreboardDisplaySlot.LIST, objective);
	}

}
