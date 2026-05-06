package me.sl.XNPlug.Functions.LagCommand;

import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.World;
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

        StringBuilder msg = new StringBuilder("§6已运行时间: §f");

        long time = System.currentTimeMillis() - XNPlug.getEnableTime();
        if (time >= 1000 * 86400) msg.append(time / (1000 * 86400)).append(" 日 ");
        if (time >= 1000 * 3600) msg.append((time % (1000 * 86400)) / (1000 * 3600)).append(" 时 ");
        if (time >= 1000 * 60) msg.append((time % (1000 * 3600)) / (1000 * 60)).append(" 分 ");
        msg.append((time % (1000 * 60)) / 1000).append(" 秒\n");

        msg.append("§6TPS=");
        for (double tps : Bukkit.getServer().getTPS()) {
            msg.append("%s%.3f ".formatted(
                    tps >= 20 ? "§b" :
                            tps >= 18 ? "§a" :
                            tps >= 16 ? "§e" : "§c",
                    tps
            ));
        }
        msg.append("\n");

        double mspt = Arrays.stream(Bukkit.getServer().getTickTimes()).average().orElse(0.0) / 1000000;
        msg.append("§6MSPT=%s%.2f\n§6瞬时内存占用: §f%d MB / %d MB\n".formatted(
                mspt < 5 ? "§b" :
                        mspt < 40 ? "§a" :
                        mspt < 50 ? "§e" : "§c",
                mspt,
                (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024,
                Runtime.getRuntime().totalMemory() / 1024 / 1024
        ));

        // 遍历所有世界
        for (World world : Bukkit.getWorlds()) {
            msg.append("§6世界 §c%s§6 类型: §c%s§6 区块数量: §c%d§6 实体数量: §c%d\n".formatted(
                    world.getName(),
                    world.getEnvironment().name(),
                    world.getLoadedChunks().length,
                    world.getEntities().size()
            ));
        }

        sender.sendMessage(msg.toString());

        return true;
    }
}
