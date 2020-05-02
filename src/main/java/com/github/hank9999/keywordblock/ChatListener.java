package com.github.hank9999.keywordblock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("KeywordBlock.admin")) {
            return;
        }
        if (!event.isCancelled()) {
            String username = player.getName();
            String text = ChatColor.stripColor(event.getMessage());
            String text_low = text.toLowerCase().replace(" ", "");
            String key_text = text_low.replaceAll("&[0-9]|&[a-z]", "");
            String key_text2 = key_text.replaceAll("[^a-zA-Z0-9\\\\u4E00-\\\\u9FA5]", "").replaceAll("[\\s*|\t|\r|\n]", "");
            String key_text3 = key_text2.replaceAll("[^\\\\u4E00-\\\\u9FA5]", "");
            String key_text4 = key_text.replaceAll("%[0-9]|%[a-z]", "");
            String key_text5 = key_text4.replaceAll("[^a-zA-Z0-9\\\\u4E00-\\\\u9FA5]", "").replaceAll("[\\s*|\t|\r|\n]", "");
            for (String keyword : KeywordBlock.plugin.getConfig().getStringList("words")) {
                keyword = keyword.toLowerCase();
                if (keyword.equalsIgnoreCase("")) {
                    continue;
                }
                if (text_low.contains(keyword) || key_text.contains(keyword) || key_text2.contains(keyword) || key_text3.contains(keyword) || key_text4.contains(keyword) || key_text5.contains(keyword)) {
                    event.setCancelled(true);
                    for (String warn_message : KeywordBlock.plugin.getConfig().getStringList("message.warn.player")) {
                        player.sendMessage(Lib.color_translate(warn_message.replace("%player_name%", username).replace("%player_message%", text)));
                    }
                    if (KeywordBlock.plugin.getConfig().getBoolean("function.admin-broadcast")) {
                        for (String broadcast_message : KeywordBlock.plugin.getConfig().getStringList("message.broadcast.admin")) {
                            broadcast_message = Lib.color_translate(broadcast_message.replace("%player_name%", username).replace("%player_message%", text));
                            KeywordBlock.plugin.getServer().broadcast(broadcast_message, "KeywordBlock.admin");
                        }
                    }
                    break;
                }
            }
        }
    }
}
