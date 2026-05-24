package me.sl.XNPlug.Functions.GetIP;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TellIPListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 告知所有 OP
        for (Player op : Bukkit.getOnlinePlayers()) {
            if (op.hasPermission("xnplug.show_getip")) {
                op.sendMessage("§6玩家 %s 来自 §a%s".formatted(
                        player.getName(),
                        GetIP.getExactLocation(player)
                ));
            }
        }
    }
}
