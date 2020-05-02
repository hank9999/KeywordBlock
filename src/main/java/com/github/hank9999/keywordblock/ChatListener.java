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
            String key_text_a = text_low.replaceAll("&[0-9]|&[a-z]", "");
            String key_text_b = text_low.replaceAll("%[0-9]|%[a-z]", "");
            String key_text_c = key_text_a.replaceAll("%[0-9]|%[a-z]", "");
            String key_text_replaceall = key_text_c.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", "");
            String key_text_only_en = key_text_replaceall.replace("[^a-zA-Z]", "");
            String key_text_only_en_123 = key_text_replaceall.replace("[^a-zA-Z0-9]", "");
            String key_text_only_cn = key_text_replaceall.replace("[^\\u4E00-\\u9FA5]", "");
            String key_text_a_no = text_low.replaceAll("&[0-9]|&[a-z]", "").replaceAll("[\\s*|\t|\r|\n]", "").replaceAll("\\\\[a-z]|\\\\[A-Z]|/[A-Z]|/[a-z]","");
            String key_text_b_no = text_low.replaceAll("%[0-9]|%[a-z]", "").replaceAll("[\\s*|\t|\r|\n]", "").replaceAll("\\\\[a-z]|\\\\[A-Z]|/[A-Z]|/[a-z]","");
            String key_text_c_no = key_text_a_no.replaceAll("%[0-9]|%[a-z]", "");
            String key_text_replaceall_no = key_text_c_no.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", "");
            String key_text_only_en_no = key_text_replaceall_no.replace("[^a-zA-Z]", "");
            String key_text_only_en_123_no = key_text_replaceall_no.replace("[^a-zA-Z0-9]", "");
            String key_text_only_cn_no = key_text_replaceall_no.replace("[^\\u4E00-\\u9FA5]", "");

            String all = text_low + " " + key_text_a + " " + key_text_b + " " + key_text_c  + " " + key_text_replaceall + " " + key_text_only_en  + " " + key_text_only_en_123 + " " + key_text_only_cn + " " + key_text_a_no  + " " + key_text_b_no + " " + key_text_c_no + " " + key_text_replaceall_no  + " " + key_text_only_en_no + " " + key_text_only_en_123_no + " " + key_text_only_cn_no;

            for (String keyword : KeywordBlock.plugin.getConfig().getStringList("words")) {
                keyword = keyword.toLowerCase();
                if (keyword.equalsIgnoreCase("")) {
                    continue;
                }
                if (all.contains(keyword)) {
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
