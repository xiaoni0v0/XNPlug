package me.sl.XNPlug.Functions.Afk;

import lombok.Getter;
import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Afk {
    @Getter
    private final XNPlug plugin;
    @Getter
    private final long thresholdSeconds;

    private final Map<UUID, Long> lastActiveTime = new HashMap<>();
    private final Map<UUID, Boolean> afkStatus = new HashMap<>();

    public Afk(XNPlug plugin, long thresholdSeconds) {
        this.plugin = plugin;
        this.thresholdSeconds = thresholdSeconds;

        // 注册监听器
        plugin.getServer().getPluginManager().registerEvents(new AfkListener(this), plugin);

        // 注册命令
        Objects.requireNonNull(plugin.getCommand("afk")).setExecutor(new AfkCommand(this));
    }

    public void start() {
        // 先把所有玩家加入 Map
        for (Player player : Bukkit.getOnlinePlayers()) {
            lastActiveTime.put(player.getUniqueId(), System.currentTimeMillis());
            afkStatus.put(player.getUniqueId(), false);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        }.runTaskTimer(plugin, 2, 2);
    }

    private void tick() {
        long now = System.currentTimeMillis();

        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            Long lastActive = lastActiveTime.get(uuid);
            if (lastActive == null) {
                lastActive = now;
                lastActiveTime.put(uuid, now);
            }

            boolean isCurrentlyAfk = isAfk(uuid);

            if (!isCurrentlyAfk && (now - lastActive) >= thresholdSeconds * 1000) {
                // 变为 AFK
                setAfk(player, true);
            } else if (isCurrentlyAfk && (now - lastActive) < thresholdSeconds * 1000) {
                // 如果AFK期间移动了，自动退出AFK（由事件触发重置计时，这里只需判断状态）
                // 实际上事件中我们会调用 updateActivity() 并 remove AFK，这里不用重复处理
                // 但为了防止事件未触发（比如AFK后别人传送他），就再检查一下
                // 已经满足非空闲条件，但仍是AFK状态 → 强制退出AFK
                setAfk(player, false);
            }
        }
    }

    // 重置活动时间
    public void resetActivity(Player player) {
        UUID uuid = player.getUniqueId();
        lastActiveTime.put(uuid, 0L);
        setAfk(player, false);
    }

    // 更新活动时间（由监听器调用）
    public void updateActivity(Player player) {
        UUID uuid = player.getUniqueId();
        lastActiveTime.put(uuid, System.currentTimeMillis());
        // 如果之前是AFK状态，则自动退出AFK
        if (isAfk(uuid)) {
            setAfk(player, false);
        }
    }

    public void setAfk(Player player, boolean afk) {
        UUID uuid = player.getUniqueId();
        boolean wasAfk = isAfk(uuid);
        if (wasAfk == afk) return;

        afkStatus.put(uuid, afk);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§7§o* %s %s".formatted(
                    player.equals(p) ? "你" : player.getName(),
                    afk ? "暂时离开了" : "回来了"
            ));
        }
    }

    public boolean isAfk(UUID uuid) {
        return afkStatus.getOrDefault(uuid, false);
    }
}
