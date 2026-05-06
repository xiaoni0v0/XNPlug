package me.sl.XNPlug.Functions.WhoCommand;

import me.sl.XNPlug.Functions.GetIP.GetIP;
import me.sl.XNPlug.Utils;
import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record WhoCommand(XNPlug plugin) implements CommandExecutor {
    public WhoCommand(XNPlug plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("who")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("whois")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("info")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§e？你是 op 嘛就想看别人资料啊？");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§c请指定玩家");
            return true;
        }
        if (args.length >= 2) {
            sender.sendMessage("§c参数过多");
            return true;
        }

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage("§c玩家不存在");
            return true;
        }

        long playTimeMinutes = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 1200;
        String mode = player.getGameMode().name();
        String msg = """
                §6====== §c%s§6 的资料 ======
                §6- 名称: §f%s
                §6- UUID: §f%s
                §6- 坐标: §f(%s, %d, %d, %d)
                §6- 游戏时长: §f%d 时 %d 分
                §6- 游戏模式: §f%s
                §6- op 权限: %s
                §6- 最后一次登录: §f%s
                §6- IP 地址: §f%s
                §6- 地理位置: §f%s
                """.formatted(
                player.getName(),
                player.getName(),
                player.getUniqueId(),
                player.getWorld().getName(),
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ(),
                playTimeMinutes / 60,
                playTimeMinutes % 60,
                mode.equals("SURVIVAL") ? "生存" :
                        mode.equals("CREATIVE") ? "创造" :
                        mode.equals("ADVENTURE") ? "冒险" :
                        mode.equals("SPECTATOR") ? "旁观" : mode,
                player.isOp() ? "§a是" : "§c否",
                Utils.formatTime(player.getLastLogin()),
                GetIP.getIP(player),
                GetIP.getExactLocation(player)
        );

        sender.sendMessage(msg);

        return true;
    }
}
