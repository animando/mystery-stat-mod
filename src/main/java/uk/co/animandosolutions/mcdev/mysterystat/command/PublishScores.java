package uk.co.animandosolutions.mcdev.mysterystat.command;

import static java.lang.String.format;
import static java.util.Optional.of;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.fullyQualifiedObjectiveName;
import static uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper.getObjectiveWithName;

import java.util.List;
import java.util.Optional;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.ServerCommandSource;
import uk.co.animandosolutions.mcdev.mysterystat.discord.DiscordBot;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.MysteryObjectiveSuggestionProvider;
import uk.co.animandosolutions.mcdev.mysterystat.objectives.ObjectiveHelper;

public class PublishScores implements CommandDefinition {

    static final String PERMISSION = "mysterystat.publish";

    @Override
    public int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        var scoreboard = context.getSource().getServer().getScoreboard();
        String objectiveNameArg = getArgument(context, CommandConstants.Arguments.OBJECTIVE_NAME).orElseThrow();
        String objectiveName = fullyQualifiedObjectiveName(objectiveNameArg);

        Optional<ScoreboardObjective> maybeObjective = getScoreboardObjective(source, scoreboard, objectiveName);
        if (maybeObjective.isEmpty()) {
            return 0;
        }

        ScoreboardObjective objective = maybeObjective.get();
        publishScores(source, scoreboard, objective);

        return 1;
    }

    private void publishScores(ServerCommandSource source, ServerScoreboard scoreboard, ScoreboardObjective objective) {
        List<ObjectiveHelper.Score> topThree = ObjectiveHelper.getTopThree(scoreboard, objective);

        DiscordBot discord = DiscordBot.INSTANCE;
        if (!discord.checkConfig()) {
            sendMessage(source, "Discord not configured");
            return;
        }

        discord.sendMessage(format("Mystery Stat Leaderboard (%s)", objective.getDisplayName().getLiteralString()));
        if (topThree.size() == 0) {
            discord.sendMessage("<empty list>");
        }
        for (int position = topThree.size(); position >= 1; position--) {
            discord.sendMessage(formatListEntry(topThree.get(position - 1), position));
        }
    }

    private Optional<ScoreboardObjective> getScoreboardObjective(ServerCommandSource source,
            ServerScoreboard scoreboard, String objectiveName) {
        Optional<ScoreboardObjective> maybeObjective = getObjectiveWithName(scoreboard, objectiveName);
        if (maybeObjective.isEmpty()) {
            sendMessage(source, format("Unknown objective: %s", objectiveName));
        }
        return maybeObjective;
    }

    private String formatListEntry(final ObjectiveHelper.Score entry, final int place) {
        return format("%d: %s (%s)", place, entry.player(), entry.score());
    }

    @Override
    public String getCommand() {
        return "publish";
    }

    @Override
    public CommandDefinition.Argument<?>[] getArguments() {
        return new CommandDefinition.Argument<?>[] {
                new CommandDefinition.Argument<>(CommandConstants.Arguments.OBJECTIVE_NAME, StringArgumentType.string(),
                        false, PERMISSION, of(MysteryObjectiveSuggestionProvider.INSTANCE)) };
    }

    @Override
    public String getPermission() {
        return PERMISSION;
    }

}
