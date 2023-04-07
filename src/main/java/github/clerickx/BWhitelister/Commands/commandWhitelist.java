package github.clerickx.BWhitelister.Commands;

import github.clerickx.BWhitelister.utils.whitelist;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

public class commandWhitelist extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("whitelist")) return;

        event.deferReply().queue();

        switch (event.getOption("action").getAsString()) {
            case "list":
                event.getHook().sendFiles(FileUpload.fromData(whitelist.getWhitelistFile(), "whitelist.yml")).queue();
                break;
        }
    }

}
