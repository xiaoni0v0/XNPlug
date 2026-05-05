package me.sl.XNPlug.Functions.Afk;

import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkExpansion extends PlaceholderExpansion {
    @Getter
    private final Afk afk;

    // 构造方法，用于拿到你主插件的实例
    public AfkExpansion(Afk afk) {
        this.afk = afk;
    }

    @Override
    public @NotNull String getIdentifier() {
        // 这是占位符的标识符，例如这将生成为 %afk_xxx%
        return "afk";
    }

    @Override
    public @NotNull String getAuthor() {
        // 返回你的名字
        return "SweetLitchi";
    }

    @Override
    public @NotNull String getVersion() {
        // 返回你的插件版本
        return "1.0";
    }

    // 告诉 PAPI 这个扩展是否需要注册，一般用默认的 true 即可
    @Override
    public boolean persist() {
        return true;
    }

    // 核心解析方法
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (player == null) {
            return "";
        }

        // 处理参数，%afk_status%
        if (params.equalsIgnoreCase("status")) {
            boolean isAfk = afk.isAfk(player.getUniqueId());
            return isAfk ? "&cAFK " : "";
        }

        return null;
    }
}
