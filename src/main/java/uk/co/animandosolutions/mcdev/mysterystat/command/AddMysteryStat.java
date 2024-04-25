package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static net.minecraft.text.Text.literal;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.createObjective;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;

import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class AddMysteryStat implements CommandDefinition {
	static final String PERMISSION = "mysterystat.add";

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		sendMessage(source, "Adding mystery stat");
		var scoreboard = source.getServer().getScoreboard();

		String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME).orElseThrow();
		String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);

		String criterionName = getArgument(context, CommandConstants.Arguments.CRITERION).orElseThrow();

		var criterion = ScoreboardCriterion.getOrCreateStatCriterion(criterionName).orElse(null);
		if (criterion == null) {
			sendMessage(source, format("Unknown criterion: %s", criterionName));
			return 0;
		}

		addObjective(scoreboard, objectiveName, criterion);

		return 1;
	}

	private void addObjective(ServerScoreboard scoreboard, String objectiveName, ScoreboardCriterion criterion) {
		createObjective(scoreboard, objectiveName, literal(objectiveName), criterion);
	}

	@Override
	public String getCommand() {
		return "add";
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] {
				new CommandDefinition.Argument<>(CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string(),
						false, PERMISSION, ofNullable(null)),
				new CommandDefinition.Argument<>(CommandConstants.Arguments.CRITERION, StringArgumentType.string(),
						false, PERMISSION, ofNullable(null)), };
	}

	@Override
	public String getPermission() {
		return PERMISSION;
	}
}
