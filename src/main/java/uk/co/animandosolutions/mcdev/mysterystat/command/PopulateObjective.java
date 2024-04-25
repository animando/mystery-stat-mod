package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getObjectiveWithName;
import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;
import uk.co.animandosolutions.mcdev.mysterystat.discord.DiscordBot;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.MysteryObjectiveSuggestionProvider;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.NonMysteryObjectiveSuggestionProvider;

public class PopulateObjective implements CommandDefinition {
	private Logger logger = LOGGER;
	DiscordBot discord = DiscordBot.INSTANCE;
	static final String PERMISSION = "mysterystat.populate";

	@Override
	public int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		var scoreboard = context.getSource().getServer().getScoreboard();
		String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME).orElseThrow();
		String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);
		String sourceObjectiveNameArg = getArgument(context, CommandConstants.Arguments.SOURCE_OBJECTIVE).orElseThrow();

		Optional<ScoreboardObjective> maybeObjective = getScoreboardObjective(source, scoreboard, objectiveName);
		Optional<ScoreboardObjective> maybeSourceObjective = getScoreboardObjective(source, scoreboard, sourceObjectiveNameArg);
		if (maybeObjective.isEmpty() || maybeSourceObjective.isEmpty()) {
			return 0;
		}

		ScoreboardObjective sourceObjective = maybeSourceObjective.get();
		ScoreboardObjective objective = maybeObjective.get();
		
		try {
			populateScores(source, scoreboard, objective, sourceObjective);
		} catch (Exception e) {
			logger.error("Error populating", e);
		}

		return 1;
	}

	private void populateScores(ServerCommandSource source, ServerScoreboard scoreboard, ScoreboardObjective objective, ScoreboardObjective sourceObjective) {
		List<ObjectiveHelper.Score> allScores = ObjectiveHelper.getAllObjectiveScores(scoreboard, sourceObjective);
		source.getServer().getScoreboard().updateObjective(sourceObjective);
		Map<String, Integer> scores = new HashMap<>();
		allScores.forEach(score -> {
			scores.put(score.player(), score.score());
		});
		
		scoreboard.getKnownScoreHolders().forEach(scoreholder -> {
			String scoreboardName = scoreholder.getNameForScoreboard();
			Integer scoreValue = scores.get(scoreboardName);
			logger.info(String.format("Score for %s for player %s is %s", sourceObjective.getName(), scoreboardName, scoreValue));
			scoreboard.getOrCreateScore(scoreholder, objective, true).setScore(Optional.ofNullable(scoreValue).orElse(0));
		});

	}

	private Optional<ScoreboardObjective> getScoreboardObjective(ServerCommandSource source,
			ServerScoreboard scoreboard, String objectiveName) {
		Optional<ScoreboardObjective> maybeObjective = getObjectiveWithName(scoreboard, objectiveName);
		if (maybeObjective.isEmpty()) {
			sendMessage(source, format("Unknown objective: %s", objectiveName));
		}
		return maybeObjective;
	}

	@Override
	public String getCommand() {
		return "populate";
	}

	@Override
	public CommandDefinition.Argument<?>[] getArguments() {
		return new CommandDefinition.Argument<?>[] {
				new CommandDefinition.Argument<>(CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string(),
						false, PERMISSION, of(MysteryObjectiveSuggestionProvider.INSTANCE)),
				new CommandDefinition.Argument<>(CommandConstants.Arguments.SOURCE_OBJECTIVE,
						StringArgumentType.string(), false, PERMISSION, of(NonMysteryObjectiveSuggestionProvider.INSTANCE)) };
	}
	
	@Override
	public String getPermission() {
		return PERMISSION;
	}

}
