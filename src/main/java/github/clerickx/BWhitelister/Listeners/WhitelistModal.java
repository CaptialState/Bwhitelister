package github.clerickx.BWhitelister.Listeners;

import github.clerickx.BWhitelister.BWhitelister;
import github.clerickx.BWhitelister.utils.whitelist;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class WhitelistModal extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("whitelistform")) {
            String playername = event.getValue("playername").getAsString();
            String whitelistrole = BWhitelister.getInstance().getConfig().getString("role-on-whitelist-id");
            String logchannel = BWhitelister.getInstance().getConfig().getString("log-channel-id");
            if (whitelist.check(playername)) {
                event.reply(":x: Sorry playername already registered").setEphemeral(true).queue();
            } else {
                if (whitelist.getHashMap().containsValue(event.getUser().getId().toString())) {
                    event.reply(":x: Sorry you've already registered in the server, If this was an accident you can create a ticket for name change").setEphemeral(true).queue();
                } else {
                    whitelist.add(playername, event.getUser().getId().toString());
                    event.reply(":white_check_mark: You've been whitelisted in the name, " + playername + ". have a fun time playing").setEphemeral(true).queue();
                    if (whitelistrole != null) {
                        event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById(whitelistrole))).queue();
                    }
                    if (logchannel != null) {
                        event.getGuild().getTextChannelById(logchannel).sendMessage("Member " + event.getMember().getAsMention() + ", whitelisted as " + playername).queue();
                    }
                }
            }

        }
    }

}
