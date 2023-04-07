package github.clerickx.BWhitelister;

import github.clerickx.BWhitelister.Commands.SelfWhitelist;

import github.clerickx.BWhitelister.Commands.commandWhitelist;
import github.clerickx.BWhitelister.Listeners.WhitelistModal;
import github.clerickx.BWhitelister.Listeners.whitelistCheck;
import github.clerickx.BWhitelister.utils.whitelist;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class BWhitelister extends JavaPlugin {

    private static BWhitelister instance;
    private JDA jda;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        whitelist.create();
        whitelist.load();
    }

    @Override
    public void onEnable() {
        if (getConfig().getString("token") == null || getConfig().getString("guild-id") == null) {
            getLogger().warning("Token or Guild ID not configured");
        } else {
            try {
                jda = startJDA();
                getLogger().info("JDA Started");
            } catch (LoginException | InterruptedException e) {
                getLogger().warning(e.getMessage());
            }
            jda.getGuildById(getConfig().getString("guild-id")).updateCommands().addCommands(
                    Commands.slash("selfwhitelist", "Whitelist based on discord form"),
                    Commands.slash("whitelist", "DC controller")
                            .addOptions(
                                    new OptionData(OptionType.STRING, "action", "performs the given actions", true)
                                            .addChoice("remove", "remove")
                                            .addChoice("list", "list"),
                                    new OptionData(OptionType.STRING, "ign", "Ingame name of player", false),
                                    new OptionData(OptionType.USER, "user", "User to be actioned"))
                            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS))
            ).queue();
        }
        getServer().getPluginManager().registerEvents(new whitelistCheck(), this);
    }

    @Override
    public void onDisable() {
        whitelist.saveWhitelist();
        jda.shutdown();
    }

    private JDA startJDA() throws LoginException, InterruptedException {
        return(JDABuilder.createDefault(getConfig().getString("token"))
                .addEventListeners(new SelfWhitelist())
                .addEventListeners(new WhitelistModal())
                .addEventListeners(new commandWhitelist())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build().awaitReady());
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

}
