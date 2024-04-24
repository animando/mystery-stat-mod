package uk.co.animandosolutions.mcdev.mysterystat.command;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.server.command.ServerCommandSource;
import uk.co.animandosolutions.mcdev.mysterystat.utils.Logger;

public class ClearObjectives implements CommandDefinition {
	
	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = source.getServer().getScoreboard();
		var objectives = scoreboard.getObjectives();
		for (var it = objectives.iterator(); it.hasNext(); ) {
			var element = it.next();
			if (!element.getName().startsWith("mysterystat_")) {
				it.remove();
			}
			
		}
		Logger.LOGGER.info(String.format("removing objectives %s", String.join(", ", objectives.stream().map(s -> s.getName()).toList())));
		
		objectives.forEach(scoreboard::removeObjective);
		return 1;
	}

	@Override
	public String getCommand() {
		return "clear";
	}

}
