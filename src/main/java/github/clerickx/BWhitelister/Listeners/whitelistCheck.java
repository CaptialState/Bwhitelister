package github.clerickx.BWhitelister.Listeners;

import github.clerickx.BWhitelister.BWhitelister;
import github.clerickx.BWhitelister.utils.whitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class whitelistCheck implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (whitelist.check(event.getPlayer().getName())) {
            event.allow();
        } else {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, BWhitelister.getInstance().getConfig().getString("whitelist-message"));
        }
    }

}
