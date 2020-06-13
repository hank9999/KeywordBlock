package com.github.hank9999.keywordblock.Event;

import com.github.hank9999.keywordblock.KeywordBlock;
import com.github.hank9999.keywordblock.Libs.Lib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class ChatEvent implements Listener {

    @EventHandler
    final public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("KeywordBlock.admin") && KeywordBlock.plugin.getConfig().getBoolean("function.bypass")) {
            return;
        }
        if (!event.isCancelled()) {
            String username = player.getName();
            String text = ChatColor.stripColor(event.getMessage().trim());
            String text_low_source = text.toLowerCase();
            String text_low = text_low_source.replaceAll(" ", "");
            String all = (text + " "
                    + text_low_source + " "
                    + text_low + " "
                    + text_low.replaceAll("[^a-zA-Z]", "") + " "
                    + text_low.replaceAll("%[0-9]|%[a-z]", "") + " "
                    + text_low.replaceAll("[^0-9]", "") + " "
                    + text_low.replaceAll("[^\\u4E00-\\u9FA5]", "") + " "
                    + text_low.replaceAll("[^a-zA-Z0-9]", "") + " "
                    + text_low.replaceAll("%[0-9]|%[a-z]", "").replaceAll("\\\\[a-z]|\\\\[A-Z]|/[A-Z]|/[a-z]", "") + " "
                    + text_low.replaceAll("[\\p{P}+~$`^:=./|<>?～｀＄＾＋。、？·｜()（）＜＞￥×{}&#%@!！…*丶—【】，；‘：”“’]", "") + " "
            );

            for (String keyword1 : KeywordBlock.plugin.getConfig().getStringList("words")) {
                if (keyword1.equalsIgnoreCase("")) {
                    continue;
                }
                Pattern p1 = Pattern.compile(keyword1);
                Matcher m1 = p1.matcher(all);
                Pattern p2 = Pattern.compile(keyword1.toLowerCase());
                Matcher m2 = p2.matcher(all);
                if (m1.lookingAt() || m2.lookingAt() || all.contains(keyword1.toLowerCase())) {
                    event.setCancelled(true);
                    for (String warn_message : KeywordBlock.plugin.getConfig().getStringList("message.warn.player")) {
                        player.sendMessage(Lib.color_translate(warn_message.replaceAll("%player_name%", username).replaceAll("%player_message%", text)));
                    }
                    if (KeywordBlock.plugin.getConfig().getBoolean("function.admin-broadcast")) {
                        for (String broadcast_message : KeywordBlock.plugin.getConfig().getStringList("message.broadcast.admin")) {
                            broadcast_message = Lib.color_translate(broadcast_message.replaceAll("%player_name%", username).replaceAll("%player_message%", text));
                            KeywordBlock.plugin.getServer().broadcast(broadcast_message, "KeywordBlock.admin");
                        }
                    }
                    AtomicInteger count = KeywordBlock.plugin.times.get(username);
                    if (count == null)
                        KeywordBlock.plugin.times.put(username, new AtomicInteger(1));
                    else
                        count.incrementAndGet();
                    if (KeywordBlock.plugin.times.get(username).intValue() >= KeywordBlock.plugin.getConfig().getInt("mute.times")) {
                        for (String mute_message : KeywordBlock.plugin.getConfig().getStringList("mute.message")) {
                            player.sendMessage(Lib.color_translate(mute_message));
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String command : KeywordBlock.plugin.getConfig().getStringList("mute.command")) {
                                        KeywordBlock.plugin.getServer().dispatchCommand(
                                                KeywordBlock.plugin.getServer().getConsoleSender(),
                                                Objects.requireNonNull(
                                                        Objects.requireNonNull(
                                                                command
                                                        ).replaceAll("%player%", username)
                                                )
                                        );
                                    }
                                }
                            }.runTask(KeywordBlock.plugin);

                        }
                        KeywordBlock.plugin.times.remove(username);
                    }
                    if (KeywordBlock.plugin.say_time.isEmpty() && KeywordBlock.plugin.getConfig().getBoolean("function.keeptime")) {
                        KeywordBlock.plugin.say_time.put(username, (System.currentTimeMillis() / 1000));
                        break;
                    }
                    if (KeywordBlock.plugin.say_time.get(username) == null && KeywordBlock.plugin.getConfig().getBoolean("function.keeptime")) {
                        KeywordBlock.plugin.say_time.put(username, (System.currentTimeMillis() / 1000));
                        break;
                    }
                    break;
                }
            }
            if (KeywordBlock.plugin.say_time.get(username) != null && KeywordBlock.plugin.getConfig().getBoolean("function.keeptime")) {
                if (((System.currentTimeMillis() / 1000) - KeywordBlock.plugin.say_time.get(username)) >= KeywordBlock.plugin.getConfig().getLong("mute.keeptime")) {
                    KeywordBlock.plugin.say_time.remove(username);
                }
            }
        }
    }
}
