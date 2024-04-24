package uk.co.animandosolutions.mcdev.mysterystat.objectives;

import static java.lang.String.format;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardCriterion.RenderType;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.text.MutableText;

public class ObjectiveHelper {

	public static Optional<ScoreboardObjective> getObjectiveWithName(ServerScoreboard scoreboard, String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = scoreboard.getObjectives().stream()
				.filter(it -> it.getName().equals(objectiveName)).findFirst();
		return maybeObjective;
	}

	public static String fullyQualifiedObjectiveName(String objectiveNameArg) {
		return format("mysterystat_%s", objectiveNameArg);
	}

	public static ScoreboardObjective createObjective(ServerScoreboard scoreboard, String objectiveName, MutableText displayName,
			ScoreboardCriterion criterion) {
		return scoreboard.addObjective(objectiveName, criterion, displayName, RenderType.INTEGER, true, null);
	}

	public static void setObjectiveDisplay(ServerScoreboard scoreboard, ScoreboardObjective objective) {
		scoreboard.setObjectiveSlot(ScoreboardDisplaySlot.LIST, objective);
	}

	public static Collection<ScoreboardObjective> getMysteryObjectives(ServerScoreboard scoreboard) {
		var objectives = scoreboard.getObjectives();
		for (var it = objectives.iterator(); it.hasNext(); ) {
			var element = it.next();
			if (!element.getName().startsWith(ObjectiveConstants.OBJECTIVE_PREFIX)) {
				it.remove();
			}
			
		}
		return objectives;
	}

}
