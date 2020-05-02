package com.github.hank9999.keywordblock;

import com.github.hank9999.keywordblock.Event.ChatEvent;
import com.github.hank9999.keywordblock.Event.CommandPreprocessEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class KeywordBlock extends JavaPlugin {

    public static KeywordBlock plugin;
    public Map<String, AtomicInteger> times = new HashMap<>();

    @Override
    public void onLoad() {
        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Load");
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();

        if (getConfig().getBoolean("function.detect")) {
            getServer().getPluginManager().registerEvents(new ChatEvent(), this);
            getLogger().info(ChatColor.GOLD + "Chat Check Enable");
        } else {
            getLogger().info(ChatColor.GOLD + "Chat Check Disable");
        }
        if (getConfig().getBoolean("function.detect_other")) {
            getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
            getLogger().info(ChatColor.GOLD + "Other Command Check Enable");
        } else {
            getLogger().info(ChatColor.GOLD + "Other Command Disable");
        }

        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setExecutor(new KeywordBlock_Command());
        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setTabCompleter(new KeywordBlock_Command());
        try {
            MetricsLite metrics = new MetricsLite(this, 7376);
            getLogger().info(ChatColor.GOLD + "bStats Metrics Enable");
        } catch (Exception exception) {
            getLogger().warning("An error occurred while enabling bStats Metrics!");
        }

        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Enable");
        getLogger().info(ChatColor.GOLD + "Version v" + getDescription().getVersion());

        new Update_Checker(this, 78091).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info(ChatColor.AQUA + "There is not a new update available.");
            } else {
                getLogger().info(ChatColor.AQUA + "There is a new update " + version + " available.");
                getLogger().info(ChatColor.AQUA + "See it in https://www.spigotmc.org/resources/keywordblock.78091/");
            }
        });
    }

    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Disable");
    }
}