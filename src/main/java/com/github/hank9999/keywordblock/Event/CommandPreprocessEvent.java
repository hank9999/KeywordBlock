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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;

final public class CommandPreprocessEvent implements Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    final public void onCommands(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("KeywordBlock.admin") && Config.function.bypass) {
            return;
        }

        String username = player.getName();
        String pcommand = e.getMessage();

        for (String lcommand : Config.detect_other.command) {
            if (pcommand.toLowerCase().indexOf(lcommand.toLowerCase()) == 0) {
                String pcommand_main = pcommand.split("\\s+")[0];
                if (!pcommand_main.equalsIgnoreCase(lcommand)) {
                    return;
                }
                String m1 = pcommand.replace(lcommand, "").toLowerCase();
                for (Player p : KeywordBlock.plugin.getServer().getOnlinePlayers()) {
                    m1 = m1.replace(p.getName().toLowerCase(), "");
                }

                String text = ChatColor.stripColor(e.getMessage().trim());
                if (Libs.equalKeyword(text)) {
                    e.setCancelled(true);
                    for (String warn_message : Config.message.warn.player) {
                        player.sendMessage(Libs.textColor(warn_message.replaceAll("%player_name%", username).replaceAll("%player_message%", text)));
                    }
                    if (Config.function.admin_broadcast) {
                        for (String broadcast_message : Config.message.broadcast.admin) {
                            broadcast_message = Libs.textColor(broadcast_message.replaceAll("%player_name%", username).replaceAll("%player_message%", text));
                            KeywordBlock.plugin.getServer().broadcast(broadcast_message, "KeywordBlock.admin");
                        }
                    }

                    timesCounter.add(username, 1);
                    if (timesCounter.check(username)) {
                        for (String mute_message : Config.mute.message) {
                            player.sendMessage(Libs.textColor(mute_message));
                        }
                        for (String command : Config.mute.command) {
                            Libs.runCommand(command.replaceAll(username, ""));
                        }
                    }
                }
                break;
            }
        }
    }
}
