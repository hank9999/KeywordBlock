package com.github.hank9999.keywordblock;

import com.github.hank9999.keywordblock.Commands.Main_Command;
import com.github.hank9999.keywordblock.Event.ChatEvent;
import com.github.hank9999.keywordblock.Event.CommandPreprocessEvent;
import com.github.hank9999.keywordblock.Update.Timer_Update;
import com.github.hank9999.keywordblock.bStats.MetricsLite;
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

        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setExecutor(new Main_Command());
        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setTabCompleter(new Main_Command());
        try {
            MetricsLite metrics = new MetricsLite(this, 7376);
            getLogger().info(ChatColor.GOLD + "bStats Metrics Enable");
        } catch (Exception exception) {
            getLogger().warning("An error occurred while enabling bStats Metrics!");
        }

        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Enable");
        getLogger().info(ChatColor.GOLD + "Version v" + getDescription().getVersion());

        new Timer_Update();
    }

    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Disable");
    }
}