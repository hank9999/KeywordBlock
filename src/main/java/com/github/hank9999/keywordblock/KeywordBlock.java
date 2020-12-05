package com.github.hank9999.keywordblock;

import com.github.hank9999.keywordblock.Commands.Main_Command;
import com.github.hank9999.keywordblock.Event.ChatEvent;
import com.github.hank9999.keywordblock.Event.CommandPreprocessEvent;
import com.github.hank9999.keywordblock.Libs.Config;
import com.github.hank9999.keywordblock.Libs.timesCounter;
import com.github.hank9999.keywordblock.Update.Updater;
import com.github.hank9999.keywordblock.bStats.MetricsLite;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class KeywordBlock extends JavaPlugin {

    public static KeywordBlock plugin;

    @Override
    final public void onLoad() {
        getLogger().info(ChatColor.BLUE + "KeywordBlock Load");
    }

    @Override
    final public void onEnable() {
        plugin = this;

        getLogger().info(ChatColor.AQUA + "KeywordBlock is reading config");
        Config.loadConfig();
        getLogger().info(ChatColor.AQUA + "KeywordBlock read config successfully");

        timesCounter.add("initialization_only_for_start", 1);

        if (Config.function.detect) {
            getServer().getPluginManager().registerEvents(new ChatEvent(), this);
            getLogger().info(ChatColor.GOLD + "Chat Check Enable");
        } else {
            getLogger().info(ChatColor.GOLD + "Chat Check Disable");
        }

        if (Config.function.detect_other) {
            getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
            getLogger().info(ChatColor.GOLD + "Other Command Check Enable");
        } else {
            getLogger().info(ChatColor.GOLD + "Other Command Check Disable");
        }

        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setExecutor(new Main_Command());
        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setTabCompleter(new Main_Command());

        try {
            MetricsLite metrics = new MetricsLite(this, 7376);
            getLogger().info(ChatColor.GOLD + "bStats Metrics Enable");
        } catch (Exception exception) {
            getLogger().warning("An error occurred while enabling bStats Metrics!");
        }

        getLogger().info(ChatColor.BLUE + "KeywordBlock Enable");
        getLogger().info(ChatColor.GOLD + "Version v" + getDescription().getVersion());

        new Updater();
    }

    @Override
    final public void onDisable() {
        plugin = null;
        getLogger().info(ChatColor.BLUE + "KeywordBlock Disable");
    }
}