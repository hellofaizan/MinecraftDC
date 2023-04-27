package me.hellofaizan.minecraftdc;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class MinecraftDC extends JavaPlugin {

    private final ConsoleCommandSender commandSender = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.init();

        String serverStartMessage = Config.getServerStartMessage();
        String serverOnOffWebhookURL = Config.getServerOnOffWebhookURL();

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(getLogger()), this);

        // On Webhook Defined
        DiscordWebhook webhook = new DiscordWebhook(serverOnOffWebhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(serverStartMessage)
                .setColor(new Color(0x00ff00))
        );
        try {
            webhook.execute();
        } catch (Exception e) {
            getLogger().severe(e.getStackTrace().toString());
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        commandSender.sendMessage("[Minecraft DC] >> Thank You for using MinecraftDC! If you like this plugin, please consider giving it a star on GitHub: https://github.com/hellofaizan/MinecraftDC");
        commandSender.sendMessage("[Minecraft DC] >> Also consider Joining my Discord Server: https://discord.gg/AJTAWsJafW  or Donating to the cause https://dono.hellofaizan.me/");

    }

    @Override
    public void onDisable() {
        // When the server is stopped or reloaded send a message to the Discord Server using Webhook
        String serverOnOffWebhookURL = Config.getServerOnOffWebhookURL();
        DiscordWebhook webhook = new DiscordWebhook(serverOnOffWebhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Server Stopped Successfully!")
                .setColor(new Color(0xff0000))
        );
        try {
            webhook.execute();
        } catch (Exception e) {
            getLogger().severe(e.getStackTrace().toString());
        }
    }
}
