package me.sl.XNPlug.Functions.GetIP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record TellIPListener(GetIP getIP) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 告知所有 OP
        for (Player op : Bukkit.getOnlinePlayers()) {
            if (op.isOp()) {
                op.sendMessage(ChatColor.GOLD + "玩家 " + event.getPlayer().getName() + " 来自 " + ChatColor.GREEN + getIP.getExactLocation(player));
            }
        }
    }
}
