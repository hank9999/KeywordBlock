package com.github.hank9999.keywordblock.Libs;

import com.github.hank9999.keywordblock.KeywordBlock;

import java.util.List;

public class Config {
    public static class function {
        public static Boolean detect;
        public static Boolean admin_broadcast;
        public static Boolean mute;
        public static Boolean detect_other;
        public static Boolean bypass;
        public static Boolean keeptime;
    }

    public static class message {
        public static class broadcast {
            public static List<String> admin;
        }
        public static class warn {
            public static List<String> player;
        }
    }

    public static class mute {
        public static int times;
        public static long keeptime;
        public static List<String> command;
        public static List<String> message;
    }

    public static class detect_other {
        public static List<String> command;
    }

    public static class command_lang {
        public static String keywordblock_name;
        public static List<String> help;
        public static List<String> reload;

        public static class list {
            public static List<String> prefix;
            public static List<String> empty;
        }

        public static class add {
            public static List<String> without_key;
            public static List<String> exists;
            public static List<String> success;
        }

        public static class del {
            public static List<String> without_key;
            public static List<String> not_exist;
            public static List<String> success;
        }

        public static List<String> unknown;
        public static List<String> no_perm;
    }

    public static List<String> words;

    public static void loadConfig() {
        KeywordBlock.plugin.saveDefaultConfig();
        KeywordBlock.plugin.reloadConfig();

        setValue();
    }

    public static void reloadConfig() {
        KeywordBlock.plugin.saveDefaultConfig();
        KeywordBlock.plugin.reloadConfig();

        setValue();
    }

    public static void saveConfig() {
        KeywordBlock.plugin.saveConfig();
        reloadConfig();
    }

    public static void setConfig(String path, Object value) {
        KeywordBlock.plugin.getConfig().set(path, value);
    }

    private static String getString(String path) {
        return KeywordBlock.plugin.getConfig().getString(path);
    }

    private static List<String> getStringList(String path) {
        return KeywordBlock.plugin.getConfig().getStringList(path);
    }

    private static int getInt(String path) {
        return KeywordBlock.plugin.getConfig().getInt(path);
    }

    private static long getLong(String path) {
        return KeywordBlock.plugin.getConfig().getLong(path);
    }

    private static Boolean getBoolean(String path) {
        return KeywordBlock.plugin.getConfig().getBoolean(path);
    }

    public static void setValue() {
        function.detect = getBoolean("function.detect");
        function.admin_broadcast = getBoolean("function.admin-broadcast");
        function.mute = getBoolean("function.mute");
        function.detect_other = getBoolean("function.detect_other");
        function.bypass = getBoolean("function.bypass");
        function.keeptime = getBoolean("function.keepttime");

        message.broadcast.admin = getStringList("message.broadcast.admin");
        message.warn.player = getStringList("message.warn.player");

        mute.times = getInt("mute.times");
        mute.keeptime = getLong("mute.keeptime");
        mute.command = getStringList("mute.command");
        mute.message = getStringList("mute.message");

        detect_other.command = getStringList("detect_other.command");

        command_lang.keywordblock_name = getString("command_lang.keywordblock_name");
        command_lang.help = getStringList("command_lang.help");
        command_lang.reload = getStringList("command_lang.reload");
        command_lang.list.prefix = getStringList("command_lang.list.prefix");
        command_lang.list.empty = getStringList("command_lang.list.empty");
        command_lang.add.without_key = getStringList("command_lang.add.without_key");
        command_lang.add.exists = getStringList("command_lang.add.exists");
        command_lang.add.success = getStringList("command_lang.add.success");
        command_lang.del.without_key = getStringList("command_lang.del.without_key");
        command_lang.del.not_exist = getStringList("command_lang.del.not_exist");
        command_lang.del.success = getStringList("command_lang.del.success");
        command_lang.unknown = getStringList("command_lang.unknown");
        command_lang.no_perm = getStringList("command_lang.no_perm");

        words = getStringList("words");
    }
}
