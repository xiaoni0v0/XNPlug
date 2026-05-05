package me.sl.XNPlug.Functions.FlyCommand;

import me.sl.XNPlug.XNPlug;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public record FlyCommand(XNPlug plugin) implements CommandExecutor {
    public FlyCommand(XNPlug plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("fly")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        // 1. 检查是否为玩家
        if (!(sender instanceof Player player)) {
            sender.sendMessage("只有玩家才能用 /fly");
            return true;
        }

        // 可在此处添加权限检查，例如：if (!player.hasPermission("simplefly.use")) { ... }

        // 2. 切换飞行状态
        if (player.getAllowFlight()) {
            // 关闭飞行
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(ChatColor.RED + "飞行模式关闭！");
        } else {
            // 开启飞行
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(ChatColor.GREEN + "飞行模式开启！");
        }

        return true;
    }
}
