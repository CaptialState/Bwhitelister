package github.clerickx.BWhitelister.utils;

import github.clerickx.BWhitelister.BWhitelister;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class whitelist {

    private static File customConfigFile;
    private static FileConfiguration customConfig;
    private static HashMap<String, String> whitelistmap = new HashMap<>();
    public static FileConfiguration getWhitelist() {
        return customConfig;
    }

    public static void add(String ign, String id) {
        whitelistmap.put(ign, id);
    }

    public static void remove(String ign) {
        whitelistmap.remove(ign);
    }

    public static HashMap<String, String> getHashMap() {
        return whitelistmap;
    }

    public static Boolean check(String ign) {
        return whitelistmap.containsKey(ign);
    }

    public static void load() {
        if (getWhitelist().getConfigurationSection("whitelist") != null) {
            for (String playername : getWhitelist().getConfigurationSection("whitelist").getKeys(false)) {
                whitelistmap.put(playername, getWhitelist().getString("whitelist." + playername));
            }
        }
    }

    public static File getWhitelistFile() {
        return customConfigFile;
    }

    public static void create() {
        customConfigFile = new File(BWhitelister.getInstance().getDataFolder(), "whitelist.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                BWhitelister.getInstance().getLogger().warning(e.getMessage());
            }
        }

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public static void saveWhitelist() {
        try {
            customConfig.set("whitelist", whitelistmap);
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            BWhitelister.getInstance().getLogger().warning(e.getMessage());
        }
    }

    public static void reloadWhitelist() {
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }

}
