package com.github.hank9999.keywordblock;

import org.bukkit.plugin.java.JavaPlugin;
import com.github.hank9999.keywordblock.ChatListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
public final class KeywordBlock extends JavaPlugin {

    public static KeywordBlock plugin;

    @Override
    public void onLoad() {
        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Load");
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Enable");
        getLogger().info(ChatColor.GOLD + "Version v" + getDescription().getVersion());

        if (getConfig().getBoolean("function.detect")) {
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
            getLogger().info(ChatColor.GOLD + "Chat Check Enable");
        } else {
            getLogger().info(ChatColor.GOLD + "Chat Check Disable");
        }

        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setExecutor(new KeywordBlock_Command());
        Objects.requireNonNull(getServer().getPluginCommand("keywordblock")).setTabCompleter(new KeywordBlock_Command());

    }

    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info(ChatColor.BLUE + "Keywoord Plugin Disable");
    }
}