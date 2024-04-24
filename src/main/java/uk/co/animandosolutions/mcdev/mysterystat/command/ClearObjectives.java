package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static java.lang.String.join;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getMysteryObjectives;

import java.util.Collection;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;

public class ClearObjectives implements CommandDefinition {

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = source.getServer().getScoreboard();
		var objectives = getMysteryObjectives(scoreboard);
		String objectiveListAsString = join(", ", objectives.stream().map(s -> s.getName()).toList());

		sendMessage(source, format("removing objectives %s", objectiveListAsString));

		removeObjectives(scoreboard, objectives);
		
		return 1;
	}

	private void removeObjectives(ServerScoreboard scoreboard, Collection<ScoreboardObjective> objectives) {
		objectives.forEach(scoreboard::removeObjective);
	}

	@Override
	public String getCommand() {
		return "clear";
	}

}
