package com.github.hank9999.keywordblock.Libs;

import org.bukkit.ChatColor;

public class Lib {
    public static String color_translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}