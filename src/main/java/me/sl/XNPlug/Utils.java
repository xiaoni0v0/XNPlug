package me.sl.XNPlug;

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
}
