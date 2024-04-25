package uk.co.animandosolutions.mcdev.mysterystat.objectives;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.server.command.ServerCommandSource;

public class MysteryObjectiveSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
	public static final MysteryObjectiveSuggestionProvider INSTANCE = new MysteryObjectiveSuggestionProvider();
	private MysteryObjectiveSuggestionProvider() {
		
	}
	
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {

    	var scoreboard = context.getSource().getServer().getScoreboard();
    	ObjectiveHelper.getAllMysteryStatObjectiveIdentifiers(scoreboard).forEach(it ->{
    		builder.suggest(it);
    	});
        return builder.buildFuture();
    }
}