package me.sl.XNPlug.Functions.Afk;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import io.papermc.paper.event.player.AsyncChatEvent;

public record AfkListener(Afk afk) implements Listener {

    // 移动事件：只在真正改变方块位置时才视为活动（忽略转头、跳跃等）
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        // 如果坐标没变（例如w只是转头或改变飞行状态） -> 忽略
        if (!event.hasChangedBlock()) {
            return;
        }
        afk.updateActivity(event.getPlayer());
    }

    // 玩家交互（右键点击方块/空气）
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        afk.updateActivity(event.getPlayer());
    }

    // 玩家聊天
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        afk.updateActivity(event.getPlayer());
    }

    // 玩家执行命令
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().toLowerCase().startsWith("/afk")) {
            return; // /afk 本身不重置计时
        }
        afk.updateActivity(event.getPlayer());
    }

    // 玩家加入服务器
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        afk.updateActivity(event.getPlayer());
    }

    // 玩家离开服务器
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    }
}
