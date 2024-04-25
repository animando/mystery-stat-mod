package uk.co.animandosolutions.mcdev.mysterystat.objectives;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.server.command.ServerCommandSource;

public class NonMysteryObjectiveSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
	public static final NonMysteryObjectiveSuggestionProvider INSTANCE = new NonMysteryObjectiveSuggestionProvider();
	private NonMysteryObjectiveSuggestionProvider() {
		
	}
	
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {

    	var scoreboard = context.getSource().getServer().getScoreboard();
    	ObjectiveHelper.getAllNonMysteryStatObjectiveIdentifiers(scoreboard).forEach(it ->{
    		builder.suggest(it);
    	});
        return builder.buildFuture();
    }
}