package me.sl.XNPlug.Functions.SuicideCommand;

import me.sl.XNPlug.XNPlug;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record SuicideCommand(XNPlug plugin) implements CommandExecutor {
    public SuicideCommand(XNPlug plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("suicide")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家才能用 /suicide");
            return true;
        }

        Bukkit.broadcast(Component.text("§6%s 结束了自己的生命。".formatted(player.getName())));
        player.setHealth(0);

        return true;
    }
}
