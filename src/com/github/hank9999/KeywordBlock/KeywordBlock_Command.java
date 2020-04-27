package com.github.hank9999.KeywordBlock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordBlock_Command implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("keywordblock")) {
            if (commandSender.hasPermission("KeywordBlock.admin")) {
                if (strings.length == 0) {
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rUse &7/keywordblock help &rto get help"));
                    return true;
                }
                if (strings[0].equalsIgnoreCase("help")) {
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rUse &7/keywordblock help &rto get help"));
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rUse &7/keywordblock reload &rto reload"));
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rUse &7/keywordblock list &rto get list"));
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rUse &7/keywordblock add <keyword> &rto add"));
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rUse &7/keywordblock del <keyword> &rto del"));
                    return true;
                }
                if (strings[0].equalsIgnoreCase("reload")) {
                    KeywordBlock.plugin.reloadConfig();
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rReload Config"));
                    return true;
                }
                if (strings[0].equalsIgnoreCase("list")) {
                    commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rKeyword List:"));
                    if (KeywordBlock.plugin.getConfig().getStringList("words").size() == 0) {
                        commandSender.sendMessage(Lib.color_translate(" - &3Null"));
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
                    try {
                        strings[1] = strings[1];
                    } catch (Exception e) {
                        commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rPlease add with keyword"));
                        return true;
                    }
                    List<String> temp = KeywordBlock.plugin.getConfig().getStringList("words");
                    if (temp.contains(strings[1])) {
                        commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rThis keyword already exists"));
                    } else {
                        temp.add(strings[1]);
                        KeywordBlock.plugin.getConfig().set("words", temp);
                        KeywordBlock.plugin.saveConfig();
                        KeywordBlock.plugin.reloadConfig();
                        String message = Lib.color_translate("&2[&eKeywordBlock&2] &9" + commandSender.getName() + " &radd a keyword &3" + strings[1]);
                        KeywordBlock.plugin.getServer().broadcast(message, "KeywordBlock.admin");
                    }

                    return true;
                }
                if (strings[0].equalsIgnoreCase("del")) {
                    try {
                        strings[1] = strings[1];
                    } catch (Exception e) {
                        commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rPlease del with keyword"));
                        return true;
                    }
                    List<String> temp = KeywordBlock.plugin.getConfig().getStringList("words");
                    if (temp.contains(strings[1])) {
                        temp.remove(strings[1]);
                        KeywordBlock.plugin.getConfig().set("words", temp);
                        KeywordBlock.plugin.saveConfig();
                        KeywordBlock.plugin.reloadConfig();
                        String message = Lib.color_translate("&2[&eKeywordBlock&2] &9" + commandSender.getName() + " &rdel a keyword &3" + strings[1]);
                        KeywordBlock.plugin.getServer().broadcast(message, "KeywordBlock.admin");

                    } else {
                        commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &rThis keyword is not exist"));
                    }
                    return true;
                }
                commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &cUnknown Command"));
                return true;

            } else {
                commandSender.sendMessage(Lib.color_translate("&2[&eKeywordBlock&2] &r&cYou don't have permission to use this command"));
                return true;
            }
        }
        return true;
    }

    private String[] Commands = {"help", "reload", "list", "add", "del"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return Arrays.asList(Commands);
        if (args[0].equalsIgnoreCase("del")) {
            List<String> keywords = KeywordBlock.plugin.getConfig().getStringList("words");
            return keywords;
        }
        if (args.length > 1) return new ArrayList<>();
        return Arrays.stream(Commands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}