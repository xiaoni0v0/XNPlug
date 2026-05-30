package me.sl.XNPlug.Functions.TpChunk;

import me.sl.XNPlug.XNPlug;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record TpChunk(XNPlug plugin) implements CommandExecutor {
    public TpChunk(XNPlug plugin) {
        this.plugin = plugin;

        // 注册命令
        Objects.requireNonNull(plugin.getCommand("tpchunk")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("xnplug.command.tpchunk")) {
            sender.sendMessage("§4？你有权限嘛就想 tpchunk 啊？");
            return true;
        }

        // 只能玩家执行
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c该命令只能由玩家执行");
            return true;
        }

        // 检查参数
        if (args.length != 2) {
            sender.sendMessage("§e用法: /tpchunk <chunk_x> <chunk_z>");
            return true;
        }
        int chunkX, chunkZ;
        try {
            chunkX = Integer.parseInt(args[0]);
            chunkZ = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage("§c请输入有效的整数");
            return true;
        }

        World world = player.getWorld();
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = world.getHighestBlockYAt(x, z) + 1;

        player.teleport(new Location(world, x, y, z));

        return true;
    }
}
