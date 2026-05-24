package me.sl.XNPlug.Functions.GetIP;

import me.sl.XNPlug.Utils;
import me.sl.XNPlug.XNPlug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public record GetIP(XNPlug plugin) implements CommandExecutor {
    public GetIP(XNPlug plugin) {
        this.plugin = plugin;

        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new TellIPListener(), plugin);
        // 注册命令
        Objects.requireNonNull(plugin.getCommand("getip")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("xnplug.command.getip")) {
            sender.sendMessage("§4？你有权限嘛就想 getip 啊？");
            return true;
        }

        Player player = Utils.getOnePlayerByCommandArgs(sender, args);
        if (player == null) return true;

        String msg = "§6玩家 %s 的IP信息: §a%s §a%s".formatted(
                player.getName(),
                getIP(player),
                getExactLocation(player)
        );

        sender.sendMessage(msg);

        return true;
    }

    /**
     * 获取玩家的 IP 地址
     *
     * @param player 玩家对象
     * @return 玩家的 IP 地址
     */
    public static String getIP(Player player) {
        try {
            return Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
        } catch (Exception e) {
            return "§c获取IP失败";
        }
    }

    /**
     * 获取玩家的地理位置信息，如国家、省份、城市、运营商等
     *
     * @param player 玩家对象
     * @return 玩家的地理位置信息
     */
    public static String getExactLocation(Player player) {
        String IP;
        try {
            IP = Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
        } catch (Exception e) {
            return "§c获取IP失败";
        }

        try {
            String body = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(URI.create("https://opendata.baidu.com/api.php?query=" + IP + "&co=&resource_id=6006&oe=utf8")).build(),
                    HttpResponse.BodyHandlers.ofString()
            ).body();
            return body.split("\"location\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            return "§c查询地理位置信息失败";
        }

    }
}
