package com.github.hank9999.keywordblock.Commands;

import com.github.hank9999.keywordblock.KeywordBlock;
import com.github.hank9999.keywordblock.Libs.Config;
import com.github.hank9999.keywordblock.Libs.Libs;
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
        if (command.getName().equalsIgnoreCase("keywordblock") || command.getName().equalsIgnoreCase("kb")) {
            String keywordblock_name = Config.command_lang.keywordblock_name;
            if (strings.length == 0) {
                commandSender.sendMessage(Libs.textColor("====================="));
                commandSender.sendMessage(Libs.textColor("     &2[&dKeywordBlock&2]"));
                commandSender.sendMessage(Libs.textColor("      &bVersion: &6v" + KeywordBlock.plugin.getDescription().getVersion()));
                commandSender.sendMessage(Libs.textColor("    &3Author: &4hank9999"));
                commandSender.sendMessage(Libs.textColor("====================="));
                return true;
            }
            if (commandSender.hasPermission("KeywordBlock.admin")) {
                if (strings[0].equalsIgnoreCase("help")) {
                    for (String message : Config.command_lang.help) {
                        commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                    }
                    return true;
                }
                if (strings[0].equalsIgnoreCase("reload")) {
                    Config.reloadConfig();

                    for (String message : Config.command_lang.reload) {
                        commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                    }
                    return true;
                }
                if (strings[0].equalsIgnoreCase("list")) {
                    for (String message : Config.command_lang.list.prefix) {
                        commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                    }

                    if (Config.words.size() == 0) {
                        for (String message : Config.command_lang.list.empty) {
                            commandSender.sendMessage(Libs.textColor(message));
                        }
                        return true;
                    }
                    for (String word : Config.words) {
                        if (word.equalsIgnoreCase("")) {
                            continue;
                        }
                        commandSender.sendMessage(Libs.textColor(" - &3" + word));
                    }
                    return true;
                }
                if (strings[0].equalsIgnoreCase("add")) {
                    if (strings.length == 1) {
                        for (String message : Config.command_lang.add.without_key) {
                            commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                        }
                        return true;
                    }
                    List<String> temp = Config.words;
                    if (temp.contains(strings[1])) {
                        for (String message : Config.command_lang.add.exists) {
                            commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                        }
                    } else {
                        temp.add(strings[1]);
                        Config.setConfig("words", temp);
                        Config.saveConfig();
                        for (String message : Config.command_lang.add.success) {
                            String message1 = Libs.textColor(keywordblock_name + " &9" + commandSender.getName() + " " + message + " &3" + strings[1]);
                            KeywordBlock.plugin.getServer().broadcast(message1, "KeywordBlock.admin");
                        }
                    }

                    return true;
                }
                if (strings[0].equalsIgnoreCase("del")) {
                    if (strings.length == 1) {
                        for (String message : Config.command_lang.del.without_key) {
                            commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                        }
                        return true;
                    }
                    List<String> temp = Config.words;
                    if (temp.contains(strings[1])) {
                        temp.remove(strings[1]);
                        Config.setConfig("words", temp);
                        Config.saveConfig();
                        for (String message : Config.command_lang.del.success) {
                            String message1 = Libs.textColor(keywordblock_name + " &9" + commandSender.getName() + " " + message + " &3" + strings[1]);
                            KeywordBlock.plugin.getServer().broadcast(message1, "KeywordBlock.admin");
                        }

                    } else {
                        for (String message : Config.command_lang.del.not_exist) {
                            commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                        }
                    }
                    return true;
                }
                for (String message : Config.command_lang.unknown) {
                    commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                }
                return true;

            } else {
                for (String message : Config.command_lang.no_perm) {
                    commandSender.sendMessage(Libs.textColor(keywordblock_name + " " + message));
                }
                return true;
            }
        }
        return true;
    }

    @Override
    final public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args[0].equalsIgnoreCase("del") && args.length == 2) {
            return Config.words;
        }
        if (args.length > 1) {
            return Collections.emptyList();
        }
        return Arrays.stream(Commands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}