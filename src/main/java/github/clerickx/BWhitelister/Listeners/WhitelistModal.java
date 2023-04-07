package github.clerickx.BWhitelister.Listeners;
import github.clerickx.BWhitelister.utils.whitelist;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class WhitelistModal extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("whitelistform")) {
            String playername = event.getValue("playername").getAsString();
            if (whitelist.check(playername)) {
                event.reply(":x: Sorry playername already registered").setEphemeral(true).queue();
            } else {
                if (whitelist.getHashMap().containsValue(event.getUser().getId().toString())) {
                    event.reply(":x: Sorry you've already registered in the server, If this was an accident you can create a ticket for name change").setEphemeral(true).queue();
                } else {
                    whitelist.add(playername, event.getUser().getId().toString());
                    event.reply(":white_check_mark: You've been whitelisted in the name, " + playername + ". have a fun time playing").setEphemeral(true).queue();
                }
            }

        }
    }

}
