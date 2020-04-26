package com.github.hank9999.KeywordBlock;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public final class KeywordBlock extends JavaPlugin {

    public static KeywordBlock plugin;

    //当插件被Load(加载)时执行
    @Override
    public void onLoad() {
        getLogger().info(ChatColor.BLUE + "关键词屏蔽插件正在加载");
    }

    //当插件被Enable(开启)时执行
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        getLogger().info(ChatColor.BLUE + "关键词屏蔽插件已启用");
        getLogger().info(ChatColor.GOLD + "版本v1.4.1");

        if (getConfig().getBoolean("function.detect")) {
            getServer().getPluginManager().registerEvents(new ChatListen(), this);
            getLogger().info(ChatColor.GOLD + "已启用消息检查");
        } else {
            getLogger().info(ChatColor.GOLD + "已禁用消息检查");
        }

        getServer().getPluginCommand("keywordblock").setExecutor(new KeywordBlock_Command());
        getServer().getPluginCommand("keywordblock").setTabCompleter(new KeywordBlock_Command());

    }


    //当插件被Disable(关闭)时执行
    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info(ChatColor.BLUE + "关键词屏蔽插件已停用");
    }
}