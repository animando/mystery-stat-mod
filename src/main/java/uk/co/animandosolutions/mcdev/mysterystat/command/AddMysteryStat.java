package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static net.minecraft.text.Text.literal;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.createObjective;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.setObjectiveDisplay;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;

public class AddMysteryStat implements CommandDefinition {

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		sendMessage(source, "Adding mystery stat");
		var scoreboard = source.getServer().getScoreboard();

		String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME);
		String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);

		String criterionName = getArgument(context, CommandConstants.Arguments.CRITERION);

		var criterion = ScoreboardCriterion.getOrCreateStatCriterion(criterionName).orElse(null);
		if (criterion == null) {
			sendMessage(source, format("Unknown criterion: %s", criterionName));
			return 0;
		}

		addAndDisplayObjective(scoreboard, objectiveName, criterion);

		return 1;
	}

	private void addAndDisplayObjective(ServerScoreboard scoreboard, String objectiveName,
			ScoreboardCriterion criterion) {
		var objective = createObjective(scoreboard, objectiveName, literal(objectiveName), criterion);
		setObjectiveDisplay(scoreboard, objective);
	}

	@Override
	public String getCommand() {
		return "add";
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] {
				new CommandDefinition.Argument<>(CommandConstants.Arguments.OBJECTIVE_NAME,
						StringArgumentType.string()),
				new CommandDefinition.Argument<>(CommandConstants.Arguments.CRITERION, StringArgumentType.string()), };
	}

}
