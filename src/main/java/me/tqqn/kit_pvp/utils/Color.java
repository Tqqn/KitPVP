package me.tqqn.kit_pvp.utils;

import org.bukkit.ChatColor;

public class Color {


    //translates color codes
    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
