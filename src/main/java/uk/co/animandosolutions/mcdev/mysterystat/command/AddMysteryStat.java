package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static net.minecraft.text.Text.literal;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardCriterion.RenderType;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.server.command.ServerCommandSource;

public class AddMysteryStat implements CommandDefinition {

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		source.getPlayerNames();
		source.sendFeedback(() -> literal("Adding mystery stat"), false);
		var scoreboard = source.getServer().getScoreboard();

		String objectiveName = String.format("mysterystat_%s", context.getArgument("objectiveName", String.class));

		var displayName = literal(format("%s: %s", "Mystery", objectiveName));

		var criterion = ScoreboardCriterion.getOrCreateStatCriterion("minecraft.mined:minecraft.dirt")
				.orElse(ScoreboardCriterion.HEALTH);
		var objective = scoreboard.addObjective(objectiveName, criterion, displayName, RenderType.INTEGER, true, null);
		objective.setDisplayAutoUpdate(true);
		scoreboard.setObjectiveSlot(ScoreboardDisplaySlot.LIST, objective);
		return 1;
	}

	@Override
	public String getCommand() {
		return "add";
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument[] {
				new CommandDefinition.Argument("objectiveName", StringArgumentType.string()) };
	}

}
