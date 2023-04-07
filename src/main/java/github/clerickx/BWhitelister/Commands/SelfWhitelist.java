package github.clerickx.BWhitelister.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class SelfWhitelist extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("selfwhitelist")) return;

        TextInput playername = TextInput.create("playername", "Player name", TextInputStyle.SHORT)
                .setPlaceholder("Minecraft playername eg: notch_mc")
                .setMaxLength(20)
                .build();

        Modal modal = Modal.create("whitelistform", "Self Whitelist")
                .addComponents(ActionRow.of(playername))
                .build();

        event.replyModal(modal).queue();

    }

}
