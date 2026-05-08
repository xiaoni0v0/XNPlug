package me.sl.XNPlug.Functions.HealCommand;

import me.sl.XNPlug.Utils;
import me.sl.XNPlug.XNPlug;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record HealCommand(XNPlug plugin) implements CommandExecutor {
    public HealCommand(XNPlug plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("heal")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§e？你是 op 嘛就想 heal 啊？");
            return true;
        }

        Player player = Utils.getOnePlayerByCommandArgs(sender, args);
        if (player == null) return true;

        // 回满生命值
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue());
        // 回满饱食度
        player.setFoodLevel(20);
        // 回满饱和度
        player.setSaturation(20);

        sender.sendMessage("§6已治疗 %s".formatted(player.getName()));

        return true;
    }
}
