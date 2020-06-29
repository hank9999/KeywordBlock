package com.github.hank9999.keywordblock.Commands;

import com.github.hank9999.keywordblock.KeywordBlock;
import com.github.hank9999.keywordblock.Libs.Lib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

final public class Main_Command implements TabExecutor {

    private final String[] Commands = {"help", "reload", "list", "add", "del"};

    @Override
    final public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("keywordblock")) {
            String keywordblock_name = KeywordBlock.plugin.getConfig().getString("command_lang.keywordblock_name");
            if (strings.length == 0) {
                commandSender.sendMessage(Lib.color_translate("====================="));
                commandSender.sendMessage(Lib.color_translate("     &2[&dKeywordBlock&2]"));
                commandSender.sendMessage(Lib.color_translate("      &bVersion: &6v" + KeywordBlock.plugin.getDescription().getVersion()));
                commandSender.sendMessage(Lib.color_translate("    &3Author: &4hank9999"));
                commandSender.sendMessage(Lib.color_translate("====================="));
                return true;
            }
            if (commandSender.hasPermission("KeywordBlock.admin")) {
                if (strings[0].equalsIgnoreCase("help")) {
                    for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.help")) {
                        commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                    }
                    return true;
                }
                if (strings[0].equalsIgnoreCase("reload")) {
                    KeywordBlock.plugin.reloadConfig();
                    for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.reload")) {
                        commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                    }
                    return true;
                }
                if (strings[0].equalsIgnoreCase("list")) {
                    for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.list.prefix")) {
                        commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                    }
                    if (KeywordBlock.plugin.getConfig().getStringList("words").size() == 0) {
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.list.empty")) {
                            commandSender.sendMessage(Lib.color_translate(message));
                        }
                        return true;
                    }
                    for (String word : KeywordBlock.plugin.getConfig().getStringList("words")) {
                        if (word.equalsIgnoreCase("")) {
                            continue;
                        }
                        commandSender.sendMessage(Lib.color_translate(" - &3" + word));
                    }
                    return true;
                }
                if (strings[0].equalsIgnoreCase("add")) {
                    if (strings.length == 1) {
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.add.without_key")) {
                            commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                        }
                        return true;
                    }
                    List<String> temp = KeywordBlock.plugin.getConfig().getStringList("words");
                    if (temp.contains(strings[1])) {
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.add.exists")) {
                            commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                        }
                    } else {
                        temp.add(strings[1]);
                        KeywordBlock.plugin.getConfig().set("words", temp);
                        KeywordBlock.plugin.saveConfig();
                        KeywordBlock.plugin.reloadConfig();
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.add.success")) {
                            String message1 = Lib.color_translate(keywordblock_name + " &9" + commandSender.getName() + " " + message + " &3" + strings[1]);
                            KeywordBlock.plugin.getServer().broadcast(message1, "KeywordBlock.admin");
                        }
                    }

                    return true;
                }
                if (strings[0].equalsIgnoreCase("del")) {
                    if (strings.length == 1) {
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.del.without_key")) {
                            commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                        }
                        return true;
                    }
                    List<String> temp = KeywordBlock.plugin.getConfig().getStringList("words");
                    if (temp.contains(strings[1])) {
                        temp.remove(strings[1]);
                        KeywordBlock.plugin.getConfig().set("words", temp);
                        KeywordBlock.plugin.saveConfig();
                        KeywordBlock.plugin.reloadConfig();
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.del.success")) {
                            String message1 = Lib.color_translate(keywordblock_name + " &9" + commandSender.getName() + " " + message + " &3" + strings[1]);
                            KeywordBlock.plugin.getServer().broadcast(message1, "KeywordBlock.admin");
                        }

                    } else {
                        for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.del.not_exist")) {
                            commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                        }
                    }
                    return true;
                }
                for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.unknown")) {
                    commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                }
                return true;

            } else {
                for (String message : KeywordBlock.plugin.getConfig().getStringList("command_lang.no_perm")) {
                    commandSender.sendMessage(Lib.color_translate(keywordblock_name + " " + message));
                }
                return true;
            }
        }
        return true;
    }

    @Override
    final public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args[0].equalsIgnoreCase("del") && args.length == 2) {
            return KeywordBlock.plugin.getConfig().getStringList("words");
        }
        if (args.length > 1) {
            return Collections.emptyList();
        }
        return Arrays.stream(Commands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}