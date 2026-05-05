package me.sl.XNPlug.Functions.GetIP;

import me.sl.XNPlug.Functions.Afk.AfkListener;
import me.sl.XNPlug.XNPlug;
import org.bukkit.ChatColor;
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
        plugin.getServer().getPluginManager().registerEvents(new TellIPListener(this), plugin);
        // 注册命令
        Objects.requireNonNull(plugin.getCommand("getip")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.YELLOW + "？你是 op 嘛就想 getip 啊？");
            return true;
        }

        Player player;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "非玩家身份执行，请指定玩家");
                return true;
            }

            player = (Player) sender;

        } else if (args.length == 1) {

            player = plugin.getServer().getPlayerExact(args[0]);

            if (player == null) {
                sender.sendMessage(ChatColor.RED + "玩家不存在");
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "参数过多");
            return true;
        }

        ((sender instanceof Player) ? sender : plugin.getServer().getConsoleSender()).sendMessage(
                ChatColor.GOLD + "玩家 " + player.getName() + " 的地理位置信息: " + ChatColor.GREEN + getExactLocation(player)
        );

        return true;
    }

    /**
     * 获取玩家的地理位置信息，如国家、省份、城市、运营商等
     *
     * @param player 玩家对象
     * @return 玩家的地理位置信息
     */
    public String getExactLocation(Player player) {
        String IP;
        try {
            IP = Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
        } catch (Exception e) {
            return ChatColor.RED + "获取 IP 失败";
        }

        try {
            String body = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(URI.create("https://opendata.baidu.com/api.php?query=" + IP + "&co=&resource_id=6006&oe=utf8")).build(),
                    HttpResponse.BodyHandlers.ofString()
            ).body();

            return body.split("\"location\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            return ChatColor.RED + "查询地理位置信息失败";
        }

    }
}
