package com.github.hank9999.keywordblock.Libs;

import com.github.hank9999.keywordblock.KeywordBlock;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Libs {
    public static String textColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Boolean equalKeyword(String text) {
        String text_low_source = text.toLowerCase();
        String text_low = text_low_source.replaceAll(" ", "");
        StringBuilder builder = new StringBuilder();
        builder.append(text);builder.append(" ");
        builder.append(text_low_source);builder.append(" ");
        builder.append(text_low);builder.append(" ");
        builder.append(text_low_source.replaceAll("%[a-z0-9]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("%[a-z0-9]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[^a-z]", ""));builder.append(" ");builder.append(" ");
        builder.append(text_low.replaceAll("[^0-9]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[^\\u4E00-\\u9FA5]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[^a-z0-9]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[^a-z\\u4E00-\\u9FA5]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[^a-z0-9\\u4E00-\\u9FA5]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[a-z0-9\\u4E00-\\u9FA5]", ""));builder.append(" ");
        builder.append(text_low.replaceAll("[\\p{P}+~$`^:=./|<>?～｀＄＾＋。、？·｜()（）＜＞￥×{}&#%@!！…*丶—【】，；‘：”“’]", ""));builder.append(" ");
        String all = builder.toString();

        for (String keyword : Config.words) {
            if (keyword.equalsIgnoreCase("")) {
                continue;
            }
            Pattern p1 = Pattern.compile(keyword);
            Matcher m1 = p1.matcher(all);
            Pattern p2 = Pattern.compile(keyword.toLowerCase());
            Matcher m2 = p2.matcher(all);
            if (m1.lookingAt() || m2.lookingAt() || all.contains(keyword.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static void runCommand(String command) {
        new BukkitRunnable() {
            @Override
            public void run() {
                KeywordBlock.plugin.getServer().dispatchCommand(
                        KeywordBlock.plugin.getServer().getConsoleSender(),
                        command
                );
            }
        }.runTask(KeywordBlock.plugin);
    }

    public static String getUrl(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "UseBlessingSkinPlugin/1.0");
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        if (responseCode == 404 || responseCode == 204) {
            return null;
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
