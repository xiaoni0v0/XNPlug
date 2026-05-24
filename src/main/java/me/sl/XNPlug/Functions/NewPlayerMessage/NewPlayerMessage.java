package me.sl.XNPlug.Functions.NewPlayerMessage;

import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public record NewPlayerMessage(XNPlug plugin) {
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission("xnplug.newplayer.message")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trm open rule_check " + player.getName());
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }
}
