package me.hellofaizan.minecraftdc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.util.logging.Logger;

public class EventListener implements Listener {

    private Logger logger;

    public EventListener(Logger logger) {
        this.logger =  logger;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Config.init();

        String serverJoinLeaveWebhookURL = Config.getServerJoinLeaveWebhookURL();
        String playerJoinMessage = Config.getPlayerJoinMessage();

        Player player = event.getPlayer();
        int playerCount = Bukkit.getOnlinePlayers().size();
        DiscordWebhook webhook = new DiscordWebhook(serverJoinLeaveWebhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("**" + player.getName() + "** " + playerJoinMessage)
                .setColor(new Color(0xFFD500))
                .setFooter("Player Count: " + playerCount+ "/"+ Bukkit.getMaxPlayers(), null)
                .setAuthor(player.getName(), "https://namemc.com/search?q="+player.getUniqueId(), "https://crafatar.com/renders/head/" + player.getUniqueId().toString() + "?overlay")
        );
        try {
            webhook.execute();
        }
        catch (Exception e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        String serverJoinLeaveWebhookURL = Config.getServerJoinLeaveWebhookURL();
        String playerLeaveMessage = Config.getPlayerLeaveMessage();

        Player player = event.getPlayer();
        DiscordWebhook webhook = new DiscordWebhook(serverJoinLeaveWebhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("**" + player.getName() + "** "+ playerLeaveMessage)
                .setAuthor(player.getName(), "https://namemc.com/search?q="+player.getUniqueId(), "https://crafatar.com/renders/head/" + player.getUniqueId().toString() + "?overlay")
                .setColor(new Color(0x4800FF))
        );
        try {
            webhook.execute();
        }
        catch (Exception e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Config.init();

        String serverChatWebhookURL = Config.getServerChatWebhookURL();
        boolean setWebhookNameAsPlayerName = Config.getSetWebhookNameAsPlayerName();

        Player player = event.getPlayer();
        String message = event.getMessage();
        DiscordWebhook webhook = new DiscordWebhook(serverChatWebhookURL);
        message = message.replaceAll("@everyone", "`@everyone`").replaceAll("@here", "`@here`").replaceAll("heart", "❤️").replaceAll("agree", "✔").replaceAll(":\\)", "☺");
        if (setWebhookNameAsPlayerName) {
            webhook.setContent("[**" + player.getName() + "**] >> " + message);
            webhook.setUsername(player.getName());
            webhook.setAvatarUrl("https://crafatar.com/renders/head/" + player.getUniqueId().toString() + "?overlay");
        } else {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("[**" + player.getName() + "**] >> " + message)
                    .setColor(new Color(0x00FFFF))
                    .setAuthor(player.getName(), "https://namemc.com/search?q="+player.getUniqueId(), "https://crafatar.com/renders/head/" + player.getUniqueId().toString() + "?overlay"));
        }
        try {
            webhook.execute();
        }
        catch (Exception e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        String serverDeathWebhookURL = Config.getPlayerDeathWebhookURL();
        DiscordWebhook webhook = new DiscordWebhook(serverDeathWebhookURL);

        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("[**" + player.getName() + "**] "+ event.getDeathMessage())
                .setColor(new Color(0xFF5900))
                .setAuthor(player.getName(), "https://namemc.com/search?q="+player.getUniqueId(), "https://crafatar.com/renders/head/" + player.getUniqueId().toString() + "?overlay")
        );
        try {
            webhook.execute();
        }
        catch (Exception e) {
            logger.severe(e.getStackTrace().toString());
        }

    }
}
