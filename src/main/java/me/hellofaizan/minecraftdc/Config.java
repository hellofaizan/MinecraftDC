package me.hellofaizan.minecraftdc;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static FileConfiguration config;

    public static void init() {
        config = MinecraftDC.getPlugin(MinecraftDC.class).getConfig();

        config.addDefault("ServerStartMessage", "Server Started Successfully!");
        config.addDefault("PlayerJoinMessage", "joined the server!");
        config.addDefault("PlayerLeaveMessage", "left the server!");
        config.addDefault("ServerOnOffWebhookURL", "https://discord.com/api/webhooks/1234567890/1234567890");
        config.addDefault("ServerJoinLeaveWebhookURL", "https://discord.com/api/webhooks/1234567890/1234567890");
        config.addDefault("ServerChatWebhookURL", "https://discord.com/api/webhooks/1234567890/1234567890");
        config.addDefault("PlayerDeathWebhookURL", "https://discord.com/api/webhooks/1234567890/1234567890");
        config.addDefault("SetWebhookNameAsPlayerName", true);
    }

    public static String getServerStartMessage() {
        return config.getString("ServerStartMessage");
    }

    public static String getPlayerJoinMessage() {
        return config.getString("PlayerJoinMessage");
    }

    public static String getPlayerLeaveMessage() {
        return config.getString("PlayerLeaveMessage");
    }

    public static String getServerOnOffWebhookURL() {
        return config.getString("ServerOnOffWebhookURL");
    }

    public static String getServerJoinLeaveWebhookURL() {
        return config.getString("ServerJoinLeaveWebhookURL");
    }

    public static String getServerChatWebhookURL() {
        return config.getString("ServerChatWebhookURL");
    }

    public static String getPlayerDeathWebhookURL() {
        return config.getString("PlayerDeathWebhookURL");
    }

    public static boolean getSetWebhookNameAsPlayerName() {
        return config.getBoolean("SetWebhookNameAsPlayerName");
    }
}
