package com.github.hank9999.keywordblock.Libs;

import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Libs {
    public static String textColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
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
