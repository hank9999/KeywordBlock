package com.github.hank9999.keywordblock.Event;

import com.github.hank9999.keywordblock.KeywordBlock;
import com.github.hank9999.keywordblock.Libs.Lib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class CommandPreprocessEvent implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    final public void onCommands(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("KeywordBlock.admin") && KeywordBlock.plugin.getConfig().getBoolean("function.bypass")) {
            return;
        }
        if (!e.isCancelled()) {
            String username = player.getName();
            String pcommand = e.getMessage();
            for (String lcommand : KeywordBlock.plugin.getConfig().getStringList("detect_other.command")) {
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
                    String text_low_source = text.toLowerCase();
                    String text_low = text_low_source.replaceAll(" ", "");
                    StringBuilder builder = new StringBuilder();
                    builder.append(text);
                    builder.append(" ");
                    builder.append(text_low_source);
                    builder.append(" ");
                    builder.append(text_low);
                    builder.append(" ");
                    builder.append(text_low.replaceAll("[^a-zA-Z]", ""));
                    builder.append(" ");
                    builder.append(text_low.replaceAll("%[0-9]|%[a-z]", ""));
                    builder.append(" ");
                    builder.append(text_low.replaceAll("[^0-9]", ""));
                    builder.append(" ");
                    builder.append(text_low.replaceAll("[^\\u4E00-\\u9FA5]", ""));
                    builder.append(" ");
                    builder.append(text_low.replaceAll("[^a-zA-Z0-9]", ""));
                    builder.append(" ");
                    builder.append(text_low.replaceAll("%[0-9]|%[a-z]", "").replaceAll("\\\\[a-z]|\\\\[A-Z]|/[A-Z]|/[a-z]", ""));
                    builder.append(" ");
                    builder.append(text_low.replaceAll("[\\p{P}+~$`^:=./|<>?～｀＄＾＋。、？·｜()（）＜＞￥×{}&#%@!！…*丶—【】，；‘：”“’]", ""));
                    builder.append(" ");
                    String all = builder.toString();

                    for (String keyword1 : KeywordBlock.plugin.getConfig().getStringList("words")) {
                        if (keyword1.equalsIgnoreCase("")) {
                            continue;
                        }
                        String keyword1_lowercase = keyword1.toLowerCase();
                        Pattern p01 = Pattern.compile(keyword1);
                        Matcher m01 = p01.matcher(all);
                        Pattern p02 = Pattern.compile(keyword1_lowercase);
                        Matcher m02 = p02.matcher(all);
                        if (m01.lookingAt() || m02.lookingAt() || all.contains(keyword1_lowercase)) {
                            e.setCancelled(true);
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
                                    KeywordBlock.plugin.getServer().dispatchCommand(
                                            KeywordBlock.plugin.getServer().getConsoleSender(),
                                            Objects.requireNonNull(
                                                    Objects.requireNonNull(
                                                            KeywordBlock.plugin.getConfig().getString("mute.command")
                                                    ).replaceAll("%player%", username)
                                            )
                                    );
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
