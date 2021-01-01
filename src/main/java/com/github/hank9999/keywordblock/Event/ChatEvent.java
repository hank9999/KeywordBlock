package com.github.hank9999.keywordblock.Event;

import com.github.hank9999.keywordblock.KeywordBlock;
import com.github.hank9999.keywordblock.Libs.Config;
import com.github.hank9999.keywordblock.Libs.Libs;
import com.github.hank9999.keywordblock.Libs.timesCounter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

final public class ChatEvent implements Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    final public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("KeywordBlock.admin") && Config.function.bypass) {
            return;
        }

        String username = player.getName();
        String text = ChatColor.stripColor(event.getMessage().trim());

        if (Libs.equalKeyword(text)) {
            event.setCancelled(true);
            for (String warn_message : Config.message.warn.player) {
                player.sendMessage(Libs.textColor(warn_message.replaceAll("%player_name%", username).replaceAll("%player_message%", text)));
            }
            if (Config.function.admin_broadcast) {
                for (String broadcast_message : KeywordBlock.plugin.getConfig().getStringList("message.broadcast.admin")) {
                    broadcast_message = Libs.textColor(broadcast_message.replaceAll("%player_name%", username).replaceAll("%player_message%", text));
                    KeywordBlock.plugin.getServer().broadcast(broadcast_message, "KeywordBlock.admin");
                }
            }

            timesCounter.add(username, 1);
            if (timesCounter.check(username)) {
                for (String mute_message : Config.mute.message) {
                    player.sendMessage(Libs.textColor(mute_message));
                }
                if (Config.function.mute) {
                    for (String command : Config.mute.command) {
                        Libs.runCommand(command.replaceAll("%player%", username));
                    }
                }
            }
        }
    }
}
