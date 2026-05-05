package me.sl.XNPlug.Functions.ListCommand;

import lombok.Getter;
import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        StringBuilder msg = new StringBuilder(ChatColor.GOLD + "当前有 "
                + ChatColor.RED + Bukkit.getOnlinePlayers().size()
                + ChatColor.GOLD + " 人在线。\n"
                + ChatColor.GOLD + "list"
                + ChatColor.WHITE + ": ");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (XNPlug.getAfk().isAfk(player.getUniqueId())) {
                msg.append(ChatColor.GRAY);
                msg.append(ChatColor.ITALIC);
                msg.append("[AFK]");
            } else {
                msg.append(ChatColor.GREEN);
            }
            msg.append(player.getName());
            msg.append(' ');
        }

        sender.sendMessage(msg.toString());
        return true;
    }
}
