package me.sl.XNPlug;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static final ZoneId ZONE_SHANGHAI = ZoneId.of("Asia/Shanghai");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 格式化时间，返回 yyyy-MM-dd HH:mm:ss 格式的字符串（北京时间）
     *
     * @param millis 时间戳（毫秒）
     * @return 格式化后的字符串，无效时返回 "????-??-?? ??:??:??"
     */
    public static String formatTime(long millis) {
        if (millis <= 0) return "????-??-?? ??:??:??";

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZONE_SHANGHAI).format(FORMATTER);
    }

    /**
     * 根据命令的参数获取一个玩家对象。
     * 注意！错误时会向 sender 发送信息，并返回 null。
     *
     * @param sender 命令发送者。玩家、控制台或者其他的
     * @param args   命令参数
     * @return 玩家对象，无效时返回 null
     */
    public static @Nullable Player getOnePlayerByCommandArgs(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {

        Player player;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§c非玩家身份执行，请指定玩家");
                return null;
            }

            player = (Player) sender;

        } else if (args.length == 1) {

            player = Bukkit.getPlayerExact(args[0]);

            if (player == null) {
                sender.sendMessage("§c玩家不存在");
                return null;
            }
        } else {
            sender.sendMessage("§c参数过多");
            return null;
        }

        return player;

    }
}
