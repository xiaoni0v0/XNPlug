package me.sl.XNPlug.Functions.ListCommand;

import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record ListCommand(XNPlug plugin) implements CommandExecutor {

    public ListCommand(XNPlug plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("list")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        StringBuilder msg = new StringBuilder("""
                §6当前有 §c%d§6 人在线。
                §6list:""".formatted(
                Bukkit.getOnlinePlayers().size()
        ));

        for (Player player : Bukkit.getOnlinePlayers()) {
            msg.append(' ');
            msg.append(XNPlug.getAfk().isAfk(player.getUniqueId()) ? "§7§o[AFK]" : "§a");
            msg.append(player.getName());
        }

        sender.sendMessage(msg.toString());

        return true;
    }
}
