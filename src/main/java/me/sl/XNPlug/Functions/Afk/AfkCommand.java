package me.sl.XNPlug.Functions.Afk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record AfkCommand(Afk afk) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("只有玩家才能用 /afk");
            return true;
        }

        if (afk.isAfk(p.getUniqueId())) {
            // 已经是AFK模式，手动退出
            afk.updateActivity(p);
        } else {
            // 手动进入AFK
            afk.resetActivity(p);
        }
        return true;
    }
}
