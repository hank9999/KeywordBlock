package com.github.hank9999.keywordblock.Update;

import com.github.hank9999.keywordblock.KeywordBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Timer;
import java.util.TimerTask;

public class Timer_Update {
    private boolean is_first = true;

    public Timer_Update() {
        final Timer timer = new Timer(true); // We use a timer cause the Bukkit scheduler is affected by server lags
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!KeywordBlock.plugin.isEnabled()) { // Plugin was disabled
                    timer.cancel();
                    return;
                }
                Bukkit.getScheduler().runTask(KeywordBlock.plugin, () -> Update_Check());
            }
        }, 0, 1000 * 60 * 60);
    }

    public void Update_Check() {
        new Update_Checker(KeywordBlock.plugin, 78091).getVersion(version -> {
            if (("v" + KeywordBlock.plugin.getDescription().getVersion()).equalsIgnoreCase(version)) {
                if (is_first) {
                    KeywordBlock.plugin.getLogger().info(ChatColor.AQUA + "No new update available.");
                    is_first = false;
                }
            } else {
                KeywordBlock.plugin.getLogger().info(ChatColor.AQUA + "A new update " + version + " available!");
                KeywordBlock.plugin.getLogger().info(ChatColor.AQUA + "See it in https://www.spigotmc.org/resources/keywordblock.78091/");
            }
        });
    }
}
