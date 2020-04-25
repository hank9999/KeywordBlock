package com.github.hank9999.KeywordBlock;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListen implements Listener {

    //监听用户名 和 用户消息内容
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("KeywordBlock.admin")) {
            return;
        }
        if (!event.isCancelled()) {  // 判断是否被取消
            String username = player.getName();
            String text = ChatColor.stripColor(event.getMessage());  //获取消息
            String key_text = text.replaceAll("&[0-9]|&[a-f]|&[k-o]|&r","");
            key_text = key_text.replaceAll( "[\\p{P}+~$`^=./|<>?～｀＄＾＋。、？·｜()（）＜＞￥×{}&#%@!！……*]" , "");
            key_text = key_text.replace(" ","");
            for (String keyword : KeywordBlock.plugin.getConfig().getStringList("words")) {
                if (keyword.equalsIgnoreCase("")) {
                    continue;
                }
                if (key_text.contains(keyword)) {
                    event.setCancelled(true);
                    for (String warn_message : KeywordBlock.plugin.getConfig().getStringList("message.warn.player")) {
                        player.sendMessage(Lib.color_translate(warn_message.replace("%player_name%", username).replace("%player_message%", text)));
                        break;
                    }
                    if (KeywordBlock.plugin.getConfig().getBoolean("function.admin-broadcast")) {
                        for (String broadcast_message : KeywordBlock.plugin.getConfig().getStringList("message.broadcast.admin")) {
                            broadcast_message = Lib.color_translate(broadcast_message.replace("%player_name%", username).replace("%player_message%", text));
                            KeywordBlock.plugin.getServer().broadcast(broadcast_message, "KeywordBlock.admin");
                            KeywordBlock.plugin.getLogger().info(broadcast_message);
                            break;
                        }
                    }
                }
            }
        }
    }
}