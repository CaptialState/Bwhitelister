package github.clerickx.BWhitelister.Commands;

import github.clerickx.BWhitelister.utils.whitelist;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

import java.util.Objects;

public class commandWhitelist extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("whitelist")) return;

        event.deferReply().queue();

        switch (Objects.requireNonNull(event.getOption("action")).getAsString()) {
            case "list":
                event.getHook().sendFiles(FileUpload.fromData(whitelist.getWhitelistFile(), "whitelist.yml")).queue();
                break;
            case "remove":
                String ign = Objects.requireNonNull(event.getOption("ign")).getAsString();
                Member user = Objects.requireNonNull(event.getOption("user")).getAsMember();

                if (ign.equals("")) {
                    if (whitelist.check(ign)) {
                        whitelist.remove(ign);
                        event.getHook().sendMessage(":white_check_mark: " + ign + ", Removed").queue();
                    } else {
                        event.getHook().sendMessage(":x: Ign not found").queue();
                    }
                } else if (user != null) {
                    for (String player : whitelist.getHashMap().keySet()) {
                        if (whitelist.getHashMap().get(player).equals(user.getId())) {
                            whitelist.remove(player);
                            event.getHook().sendMessage(":white_check_mark: " + player + ", Removed").queue();
                            return;
                        }
                    }
                    event.getHook().sendMessage(":x: ID not linked to any player").queue();
                } else {
                    event.getHook().sendMessage(":x: Either IGN or Discord id should be provided for this function to work").queue();
                }
        }
    }

}
