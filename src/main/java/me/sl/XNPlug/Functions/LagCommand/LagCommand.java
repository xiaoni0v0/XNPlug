package me.sl.XNPlug.Functions.LagCommand;

import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public record LagCommand(XNPlug plugin) implements CommandExecutor {
    public LagCommand(XNPlug plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("lag")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("gc")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        StringBuilder msg = new StringBuilder(ChatColor.GOLD + "已运行时间：" + ChatColor.WHITE);
        long time = System.currentTimeMillis() - XNPlug.getEnableTime();
        if (time > 1000 * 86400) msg.append(time / (1000 * 86400)).append(" 日 ");
        if (time > 1000 * 3600) msg.append((time % (1000 * 86400)) / (1000 * 3600)).append(" 时 ");
        if (time > 1000 * 60) msg.append((time % (1000 * 3600)) / (1000 * 60)).append(" 分 ");
        msg.append((time % (1000 * 60)) / 1000).append(" 秒\n");

        msg.append(ChatColor.GOLD + "TPS=");
        for (double tps : Bukkit.getServer().getTPS()) {
            msg.append(tps > 20 ? ChatColor.AQUA :
                            tps >= 18 ? ChatColor.GREEN :
                            tps >= 16 ? ChatColor.YELLOW :
                            ChatColor.RED)
                    .append(String.format("%.3f", tps)) // 显示小数点后 3 位
                    .append(" ");
        }
        msg.append("\n");

        msg.append(ChatColor.GOLD + "MSPT=");
        double mspt = Arrays.stream(Bukkit.getServer().getTickTimes()).average().orElse(0.0) / 1000000;
        msg.append(mspt < 5 ? ChatColor.AQUA :
                mspt < 40 ? ChatColor.GREEN :
                mspt < 50 ? ChatColor.YELLOW :
                ChatColor.RED).append(String.format("%.2f", mspt)).append("\n");

        msg.append(ChatColor.GOLD + "内存占用：" + ChatColor.WHITE)
                .append((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024)
                .append(" MB / ")
                .append(Runtime.getRuntime().totalMemory() / 1024 / 1024)
                .append(" MB");

        sender.sendMessage(msg.toString());

        return true;
    }
}
